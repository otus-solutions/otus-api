package br.org.otus.monitoring.laboratory;

import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import org.bson.Document;
import org.ccem.otus.model.monitoring.laboratory.OrphanExamsProgressDTO;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryProgressDaoBean implements LaboratoryProgressDao {

    @Inject
    private ExamResultDao examResultDao;

    @Override
    public OrphanExamsProgressDTO getOrphanExams() {
        List<Document> query = new ArrayList<>();
        query.add(new Document( "$match",new Document("aliquotValid",false)));
        query.add(new Document( "$group",new Document("_id",new Document("examId","$examId").append("examName","$examName"))));
        query.add(new Document( "$group",new Document("_id","$_id.examName").append("examIds",new Document("$push","$_id.examId")).append("count",new Document("$sum",1))));
        query.add(new Document( "$group",new Document("_id",new Document()).append("OrphanExamsProgress",new Document("$push",new Document("title","$_id").append("orphans","$count")))));

        return OrphanExamsProgressDTO.deserialize(examResultDao.aggregate(query).first().toJson());
    }
}
