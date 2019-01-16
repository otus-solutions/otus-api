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
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.waiting;

public class LaboratoryProgressDaoBean implements LaboratoryProgressDao {

    @Inject
    private ExamResultDao examResultDao;
    @Inject
    private AliquotDao aliquotDao;

    @Override
    public LaboratoryProgressDTO getDataOrphanByExams() throws DataNotFoundException {
        return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getOrphansQuery());
    }

    @Override
    public LaboratoryProgressDTO getDataQuantitativeByTypeOfAliquots(String center) throws DataNotFoundException {
        LaboratoryProgressDTO fullDTO;
        LaboratoryProgressDTO partialDTO = new LaboratoryProgressDTO();
        Document SecondPartOfDTO = null;

        fullDTO = aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getQuantitativeByTypeOfAliquotsFirstPartialResultQuery(center));

        Document fetchCenterAliquotCodesDocument = fetchAliquotCodes(center);

        if (fetchCenterAliquotCodesDocument != null) {
            Object aliquotCodes = fetchCenterAliquotCodesDocument.get("aliquotCodes");
            Document fetchAliquotCodesInExamLot = examResultDao.aggregate(new LaboratoryProgressQueryBuilder().getAliquotCodesInExamLotQuery((ArrayList<String>) aliquotCodes)).first();
            if (fetchAliquotCodesInExamLot != null) {
                Object aliquotCodesinExam = fetchAliquotCodesInExamLot.get("aliquotCodes");
                SecondPartOfDTO = aliquotDao.aggregate(new LaboratoryProgressQueryBuilder().getQuantitativeByTypeOfAliquotsSecondPartialResultQuery((ArrayList<String>) aliquotCodesinExam)).first();
            }
        }

        if (SecondPartOfDTO != null) {
            partialDTO = LaboratoryProgressDTO.deserialize(SecondPartOfDTO.toJson());
        }

        fullDTO.concatReceivedToAliquotStats(partialDTO);
        return fullDTO;
    }

    @Override
    public LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) throws DataNotFoundException {

        CompletableFuture<Document> aliquotsWithExams = this.fetchAliquotsWithExams();

        CompletableFuture<LaboratoryProgressDTO> greetingFuture = aliquotsWithExams.thenApply(allAliquotsWithExamsDocument -> {
            LaboratoryProgressDTO laboratoryProgressDTO;
            LaboratoryProgressDTO laboratoryProgressPartialDTO = new LaboratoryProgressDTO();

            if (allAliquotsWithExamsDocument != null) {

                Object allAliquotCodesinExams = allAliquotsWithExamsDocument.get("aliquotCodes");

                CompletableFuture<Document> Future1 = CompletableFuture.supplyAsync(() -> aliquotDao.aggregate(new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotFirstPartialResultQuery((ArrayList<String>) allAliquotCodesinExams,center)).first());

                CompletableFuture<Document> Future2 = CompletableFuture.supplyAsync(() -> aliquotDao.aggregate(new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotSecondPartialResultQuery((ArrayList<String>) allAliquotCodesinExams,center)).first());

                Document firstPartOfDTO = null;
                try {
                    firstPartOfDTO = Future1.get();
                } catch (InterruptedException ignored) {
                } catch (ExecutionException e) {
                    throw new IllegalStateException();
                }

                if (firstPartOfDTO == null) {
                    throw new IllegalStateException();
                }

                laboratoryProgressDTO = LaboratoryProgressDTO.deserialize(firstPartOfDTO.toJson());
                Document secondPartOfDTO = null;
                try {
                    secondPartOfDTO = Future2.get();
                } catch (InterruptedException ignored) {
                } catch (ExecutionException e) {
                    throw new IllegalStateException();
                }
                if (secondPartOfDTO != null) {
                    laboratoryProgressPartialDTO = LaboratoryProgressDTO.deserialize(secondPartOfDTO.toJson());
                }
                laboratoryProgressDTO.concatReceivedToPendingResults(laboratoryProgressPartialDTO);
            } else {
                throw new IllegalStateException();
            }

            return laboratoryProgressDTO;

        });


        return getLaboratoryProgressDTO(greetingFuture);
    }

    @Override
    public LaboratoryProgressDTO getDataByExam(String center) throws DataNotFoundException {
        Document fetchAllAliquotCodes = fetchAliquotCodes(center);
        validateFirst(fetchAllAliquotCodes);
        Object aliquotCodes = fetchAllAliquotCodes.get("aliquotCodes");
        Document first1 = examResultDao.aggregate(new LaboratoryProgressQueryBuilder().getDataByExamQuery((ArrayList<String>) aliquotCodes)).first();
        validateFirst(first1);
        return LaboratoryProgressDTO.deserialize(first1.toJson());

    }

    @Override
    public LaboratoryProgressDTO getDataOfStorageByAliquot(String center) throws DataNotFoundException {
        return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(center));
    }

    @Override
    public LaboratoryProgressDTO getDataToCSVOfPendingResultsByAliquots(String center) throws DataNotFoundException {
        CompletableFuture<Document> aliquotsWithExams = this.fetchAliquotsWithExams();
        CompletableFuture<LaboratoryProgressDTO> greetingFuture = aliquotsWithExams.thenApply(aliquotsWithExamsDocument -> {
            if (aliquotsWithExamsDocument == null) {
                throw new IllegalStateException();
            }

            Object aliquotCodes = aliquotsWithExamsDocument.get("aliquotCodes");
            Document first = aliquotDao.aggregate(new LaboratoryProgressQueryBuilder().getPendingAliquotsCsvDataQuery((ArrayList<String>) aliquotCodes,center)).first();

            if (first == null) {
                throw new IllegalStateException();
            }
            return LaboratoryProgressDTO.deserialize(first.toJson());
        });

        return getLaboratoryProgressDTO(greetingFuture);
    }

    @Nullable
    private LaboratoryProgressDTO getLaboratoryProgressDTO(CompletableFuture<LaboratoryProgressDTO> greetingFuture) throws DataNotFoundException {
        try {
            return greetingFuture.get();
        } catch (InterruptedException ignored) {
        } catch (ExecutionException e) {
            throw new DataNotFoundException(new Throwable("There are no result"));
        }

        return null;
    }

    @Override
    public LaboratoryProgressDTO getDataToCSVOfOrphansByExam() throws DataNotFoundException {
        return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery());
    }

    private Document fetchAliquotCodes(String center) {
        ArrayList<Bson> pipeline = new ArrayList<>();
        pipeline.add(parseQuery("{$match:{\"fieldCenter.acronym\":" + center + "}}"));
        pipeline.add(parseQuery("{$group:{_id:{},aliquotCodes:{$addToSet:\"$code\"}}}"));
        return aliquotDao.aggregate(pipeline).first();
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }

    private CompletableFuture<Document> fetchAliquotsWithExams() {
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
