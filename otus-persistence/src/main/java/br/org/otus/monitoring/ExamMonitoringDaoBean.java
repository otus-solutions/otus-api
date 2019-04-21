package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.model.monitoring.ParticipantExamReportDto;
import org.ccem.otus.persistence.ExamMonitoringDao;

import javax.ejb.Stateless;
import java.util.ArrayList;

@Stateless
public class ExamMonitoringDaoBean extends MongoGenericDao<Document> implements ExamMonitoringDao {

    private static final String COLLECTION_NAME = "exam_result";

    public ExamMonitoringDaoBean() {
        super(COLLECTION_NAME, Document.class);}

    @Override
    public ArrayList<ParticipantExamReportDto> getParticipantExams(Long rn) {
        return null;
    }
}

