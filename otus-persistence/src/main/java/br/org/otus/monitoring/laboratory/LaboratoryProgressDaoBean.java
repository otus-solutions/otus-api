package br.org.otus.monitoring.laboratory;

import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.monitoring.builder.LaboratoryProgressQueryBuilder;
import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LaboratoryProgressDaoBean implements LaboratoryProgressDao {

    @Inject
    private ExamResultDao examResultDao;
    @Inject
    private AliquotDao aliquotDao;

    @Override
    public LaboratoryProgressDTO getDataOrphanByExams() throws DataNotFoundException {
        return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getOrphansQuery());
    }//passou

    @Override
    public LaboratoryProgressDTO getDataQuantitativeByTypeOfAliquots(String center) throws DataNotFoundException {
        LaboratoryProgressDTO waiting = null;

            CompletableFuture<Document> Future = CompletableFuture.supplyAsync(() -> {
                try {
                    ArrayList<Bson> pipeline = new ArrayList<>();
                    pipeline.add(parseQuery("{$match:{\"role\":\"EXAM\",\"fieldCenter.acronym\":" + center + "}}"));
                    pipeline.add(parseQuery("{$group:{_id:\"$name\",aliquots:{$push:{code:\"$code\",transported:{$cond:{if:{$ne:[\"$transportationLotId\",null]},then:1,else:0}},prepared:{$cond:{if:{$ne:[\"$examLotId\",null]},then:1,else:0}}}}}}"));
                    pipeline.add(parseQuery("{$unwind:\"$aliquots\"}"));
                    pipeline.add(parseQuery("{$group:{_id:\"$_id\",transported:{$sum:\"$aliquots.transported\"},prepared:{$sum: \"$aliquots.prepared\"}}}"));
                    pipeline.add(parseQuery("{$group:{_id:{},quantitativeByTypeOfAliquots:{$push:{title:\"$_id\",transported:\"$transported\",prepared:\"$prepared\"}}}}"));
                    Document first = aliquotDao.aggregate(pipeline).first();

                    if (first == null){
                        throw new IllegalStateException("null",new DataNotFoundException("test"));
                    }
                    return first;
                } catch (Exception e) {
                    throw new IllegalStateException();
                }
            });

            CompletableFuture<Document> Future2 = CompletableFuture.supplyAsync(() -> fetchAliquotCodes(center));

            ArrayList<Bson> pipeline3 = new ArrayList<>();
            try {
                pipeline3.add(new Document("$match", new Document("aliquotCode", new Document("$in", Future2.get().get("aliquotCodes")))));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            pipeline3.add(parseQuery("{$group:{_id:\"$examId\",aliquotCodes:{$addToSet:\"$aliquotCode\"}}}"));
            pipeline3.add(parseQuery("{$unwind:\"$aliquotCodes\"}"));
            pipeline3.add(parseQuery("{$group:{_id:{},aliquotCodes:{$addToSet:\"$aliquotCodes\"}}}"));
            Document first3 = examResultDao.aggregate(pipeline3).first();

            ArrayList<Bson> pipeline4 = new ArrayList<>();
            pipeline4.add(new Document("$match", new Document("code", new Document("$in", first3.get("aliquotCodes")))));
            pipeline4.add(parseQuery("{$group:{_id:\"$name\",received:{$sum:1}}}"));
            pipeline4.add(parseQuery("{$group:{_id:{},quantitativeByTypeOfAliquots:{$push:{title:\"$_id\",received:\"$received\"}}}}"));
            Document first4 = aliquotDao.aggregate(pipeline4).first();


            try {
                waiting = LaboratoryProgressDTO.deserialize(Future.get().toJson());
            } catch (Exception e) {
                e.getCause();
            }
            waiting.concatReceivedToAliquotStats(LaboratoryProgressDTO.deserialize(first4.toJson()));

        return waiting;
    }//feito

    @Override
    public LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) throws DataNotFoundException {

        CompletableFuture<Document> aliquotsWithExams = this.fetchAliquotsWithExams();

        CompletableFuture<LaboratoryProgressDTO> greetingFuture = aliquotsWithExams.thenApply(name -> {

            CompletableFuture<LaboratoryProgressDTO> Future1 = CompletableFuture.supplyAsync(() -> {
                ArrayList<Bson> pipeline2 = new ArrayList<>();
                LaboratoryProgressDTO waiting = null;
                pipeline2.add(new Document("$match", new Document("code", new Document("$nin", name.get("aliquotCodes"))).append("fieldCenter.acronym", center)));
                pipeline2.add(parseQuery("{$group:{_id:\"$name\",waiting:{$sum:1}}}"));
                pipeline2.add(parseQuery("{$group:{_id:{},pendingResultsByAliquot:{$push:{\"title\": \"$_id\", \"waiting\":\"$waiting\"}}}}"));
                Document first = aliquotDao.aggregate(pipeline2).first();
                if (first == null) {
                    throw new IllegalStateException();
                }
                waiting = LaboratoryProgressDTO.deserialize(first.toJson());
                return waiting;
            });

            CompletableFuture<LaboratoryProgressDTO> Future2 = CompletableFuture.supplyAsync(() -> {
                ArrayList<Bson> pipeline3 = new ArrayList<>();
                LaboratoryProgressDTO received = null;
                pipeline3.add(new Document("$match", new Document("code", new Document("$in", name.get("aliquotCodes"))).append("fieldCenter.acronym", center)));
                pipeline3.add(parseQuery("{$group:{_id:\"$name\",received:{$sum:1}}}"));
                pipeline3.add(parseQuery("{$group:{_id:{},pendingResultsByAliquot:{$push:{\"title\": \"$_id\", \"received\":\"$received\"}}}}"));
                Document first = aliquotDao.aggregate(pipeline3).first();
                if (first != null) {
                    received = LaboratoryProgressDTO.deserialize(first.toJson());
                }
                return received;
            });

            LaboratoryProgressDTO laboratoryProgressDTO;
            try {
                laboratoryProgressDTO = Future1.get();
                laboratoryProgressDTO.concatReceivedToPendingResults(Future2.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new IllegalStateException();
            }
            return laboratoryProgressDTO;
        });

        try {
            return greetingFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new DataNotFoundException(new Throwable("There are no result"));
        }
    }//feito

    @Override
    public LaboratoryProgressDTO getDataByExam(String center) throws DataNotFoundException {
        Document first = fetchAliquotCodes(center);
        validateFirst(first);

        ArrayList<Bson> pipeline2 = new ArrayList<>();
        pipeline2.add(new Document("$match", new Document("aliquotCode", new Document("$in", first.get("aliquotCodes")))));
        pipeline2.add(parseQuery("{$group:{_id:{examId:\"$examId\",examName:\"$examName\"}}}"));
        pipeline2.add(parseQuery("{$group:{_id:\"$_id.examName\",received:{$sum:1}}}"));
        pipeline2.add(parseQuery("{$group:{_id:{},examsQuantitative:{$push:{title:\"$_id\",exams:\"$received\"}}}}"));
        Document first1 = examResultDao.aggregate(pipeline2).first();
        validateFirst(first1);

        return LaboratoryProgressDTO.deserialize(first1.toJson());

    }//feito

    @Override
    public LaboratoryProgressDTO getDataOfStorageByAliquot(String center) throws DataNotFoundException {
        return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(center));
    }//passou

    @Override
    public LaboratoryProgressDTO getDataToCSVOfPendingResultsByAliquots(String center) throws DataNotFoundException {
        CompletableFuture<Document> aliquotsWithExams = this.fetchAliquotsWithExams();
        CompletableFuture<LaboratoryProgressDTO> greetingFuture = aliquotsWithExams.thenApply(name -> {
            ArrayList<Bson> pipeline2 = new ArrayList<>();
            pipeline2.add(new Document("$match", new Document("code", new Document("$nin", name.get("aliquotCodes"))).append("fieldCenter.acronym", center).append("role","EXAM")));
            pipeline2.add(parseQuery("{$project:{\"code\":\"$code\",\"transported\":{$cond:{if:{$ne:[\"$transportationLotId\",null]},then:1,else:0}},prepared:{$cond:{if:{$ne:[\"$examLotId\",null]},then:1,else:0}}}}"));
            pipeline2.add(parseQuery("{$group:{_id:{},pendingAliquotsCsvData:{$push:{aliquot:\"$code\",transported:\"$transported\",prepared:\"$prepared\"}}}}"));
            Document first = aliquotDao.aggregate(pipeline2).first();
            if (first == null) {
                throw new IllegalStateException();
            }
            return LaboratoryProgressDTO.deserialize(first.toJson());
        });
        LaboratoryProgressDTO laboratoryProgressDTO = null;
        try {
            laboratoryProgressDTO = greetingFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return laboratoryProgressDTO;
    }//feito

    @Override
    public LaboratoryProgressDTO getDataToCSVOfOrphansByExam() throws DataNotFoundException {
        return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery());
    }

    private Document fetchAliquotCodes(String center) {
        ArrayList<Bson> pipeline = new ArrayList<>();
        pipeline.add(parseQuery("{$match:{\"fieldCenter.acronym\":"+center+"}}"));
        pipeline.add(parseQuery("{$group:{_id:{},aliquotCodes:{$addToSet:\"$code\"}}}"));
        return aliquotDao.aggregate(pipeline).first();
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }

    private CompletableFuture<Document> fetchAliquotsWithExams(){
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<Bson> pipeline = new ArrayList<>();
            pipeline.add(parseQuery("{$match:{\"aliquotValid\":true}}"));
            pipeline.add(parseQuery("{$group:{_id:\"$examId\",aliquotCodes:{$addToSet:\"$aliquotCode\"}}}"));
            pipeline.add(parseQuery("{$unwind:\"$aliquotCodes\"}"));
            pipeline.add(parseQuery("{$group:{_id:{},aliquotCodes:{$addToSet:\"$aliquotCodes\"}}}"));
            return examResultDao.aggregate(pipeline).first();
        });
    }

    private LaboratoryProgressDTO aliquotDaoAggregate(List<Bson> query) throws DataNotFoundException {
        Document first = aliquotDao.aggregate(query).first();
        validateFirst(first);
        return LaboratoryProgressDTO.deserialize(first.toJson());
    }

    private LaboratoryProgressDTO examResultDaoAggregate(List<Bson> query) throws DataNotFoundException {
        Document first = examResultDao.aggregate(query).first();
        validateFirst(first);
        return LaboratoryProgressDTO.deserialize(first.toJson());
    }

    private void validateFirst(Document first) throws DataNotFoundException {
        if (first == null) {
            throw new DataNotFoundException(new Throwable("There are no result"));
        }
    }


}
