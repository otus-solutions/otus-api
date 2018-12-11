package br.org.otus.monitoring.laboratory;

import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import org.bson.Document;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaboratoryProgressDaoBean implements LaboratoryProgressDao {

    @Inject
    private ExamResultDao examResultDao;
    @Inject
    private AliquotDao aliquotDao;

    @Override
    public LaboratoryProgressDTO getOrphanExams() {
        List<Document> query = new ArrayList<>();
        query.add(new Document( "$match",new Document("aliquotValid",false)));
        query.add(new Document( "$group",new Document("_id",new Document("examId","$examId").append("examName","$examName"))));
        query.add(new Document( "$group",new Document("_id","$_id.examName").append("examIds",new Document("$push","$_id.examId")).append("count",new Document("$sum",1))));
        query.add(new Document( "$group",new Document("_id",new Document()).append("orphanExamsProgress",new Document("$push",new Document("title","$_id").append("orphans","$count")))));

        return LaboratoryProgressDTO.deserialize(examResultDao.aggregate(query).first().toJson());
    }

    @Override
    public LaboratoryProgressDTO getQuantitativeByTypeOfAliquots() {
        List<Document> query = new ArrayList<>();
        query.add(new Document( "$match",
                        new Document("role","EXAM")));
        query.add(new Document( "$group",
                        new Document("_id","$name")
                        .append("aliquots",
                                new Document("$push",
                                        new Document("code","$code")
                                                .append("transported",
                                                        new Document("$cond",
                                                                new Document("if",
                                                                        new Document("$ne", Arrays.asList("$transportationLotId", null))).append("then", 1).append("else", 0)))
                                                .append("prepared",
                                                        new Document("$cond",
                                                                new Document("if",
                                                                        new Document("$ne",Arrays.asList("$examLotId", null))).append("then", 1).append("else", 0))))
                        )));
        query.add(new Document( "$unwind","$aliquots"));
        query.add(new Document( "$group",
                    new Document("_id", "$_id")
                    .append("transported",new Document("$sum", "$aliquots.transported"))
                    .append("prepared",new Document("$sum", "$aliquots.prepared"))
                    .append("aliquots",new Document("$push", "$aliquots.code"))
        ));
        query.add(new Document( "$lookup",
                        new Document("from","exam_result")
                        .append("let",new Document("aliquotCode","$aliquots"))
                        .append("pipeline",Arrays.asList(
                                new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$in", Arrays.asList("$aliquotCode",  "$$aliquotCode")))))),
                                new Document("$group",new Document("_id","$examId").append("received",new Document("$push","$aliquotCode"))),
                                new Document("$count","count")
                        ))
                        .append("as","receivedCount")
        ));
        query.add(new Document( "$group",
                        new Document("_id",new Document())
                        .append("quantitativeByTypeOfAliquots",
                                new Document("$push",
                                        new Document("title","$_id")
                                        .append("transported","$transported")
                                        .append("prepared","$prepared")
                                        .append("received",
                                                new Document("$cond",
                                                        new Document("if",
                                                                new Document("$gte",
                                                                        Arrays.asList(
                                                                                new Document("$size", "$receivedCount"),1)))
                                                                                .append("then",
                                                                                        new Document("$arrayElemAt",Arrays.asList("$receivedCount.count", 0)))
                                                                                .append("else",0)))))
        ));

        return LaboratoryProgressDTO.deserialize(aliquotDao.aggregate(query).first().toJson());
    }
}
