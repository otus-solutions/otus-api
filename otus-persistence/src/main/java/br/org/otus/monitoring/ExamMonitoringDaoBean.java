package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.monitoring.builder.ExamStatusQueryBuilder;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.ccem.otus.model.monitoring.ParticipantExamReportDto;
import org.ccem.otus.persistence.ExamMonitoringDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ExamMonitoringDaoBean extends MongoGenericDao<Document> implements ExamMonitoringDao {

    @Inject
    private LaboratoryConfigurationDao laboratoryConfigurationDao;

    private static final String COLLECTION_NAME = "exam_result";

    public ExamMonitoringDaoBean() {
        super(COLLECTION_NAME, Document.class);}

    @Override
    public ArrayList<ParticipantExamReportDto> getParticipantExams(Long rn) {
        List<String> examName = laboratoryConfigurationDao.getExamName();

        MongoCursor<Document> resultsDocument = super.aggregate(new ExamStatusQueryBuilder().getExamQuery(rn, examName)).iterator();

        ArrayList<ParticipantExamReportDto> dtos = new ArrayList<>();

        try {
            while (resultsDocument.hasNext()) {
                dtos.add(ParticipantExamReportDto.deserialize(resultsDocument.next().toJson()));
            }
        } finally {
            resultsDocument.close();
        }

        return dtos;
    }

}

