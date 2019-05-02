package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.configuration.aliquot.AliquotConfigurationQueryBuilder;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.project.exam.examInapplicability.persistence.ExamInapplicabilityDao;
import br.org.otus.monitoring.builder.ExamStatusQueryBuilder;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.ParticipantExamReportDto;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ExamMonitoringDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ExamMonitoringDaoBean extends MongoGenericDao<Document> implements ExamMonitoringDao {

    @Inject
    private LaboratoryConfigurationDao laboratoryConfigurationDao;

    @Inject
    private ParticipantLaboratoryDao participantLaboratoryDao;

    @Inject
    private ParticipantDao participantDao;

    @Inject
    private ExamInapplicabilityDao examInapplicabilityDao;

    private static final String COLLECTION_NAME = "exam_result";

    public ExamMonitoringDaoBean() {
        super(COLLECTION_NAME, Document.class);}

    @Override
    public ParticipantExamReportDto getParticipantExams(Long rn) throws DataNotFoundException {
        ParticipantExamReportDto participantExamReportDto;

        ParticipantLaboratory participantLaboratory =  participantLaboratoryDao.findByRecruitmentNumber(rn);

        Participant participant = participantDao.findByRecruitmentNumber(rn);

        Document first = laboratoryConfigurationDao.aggregate(new AliquotConfigurationQueryBuilder().getCenterAliquotsByCQQuery(participant.getFieldCenter().getAcronym(), participantLaboratory.getCollectGroupName())).first();

        List<String> examNames = laboratoryConfigurationDao.getExamName((List<String>) first.get("centerAliquots"));

        Document resultsDocument = super.aggregate(new ExamStatusQueryBuilder().getExamQuery(rn, examNames)).first();

        if(resultsDocument != null){
            participantExamReportDto = ParticipantExamReportDto.deserialize(resultsDocument.toJson());
        } else {
            resultsDocument = examInapplicabilityDao.aggregate(new ExamStatusQueryBuilder().getExamInapplicabilityQuery(rn,examNames)).first();
            if(resultsDocument != null){
                participantExamReportDto = ParticipantExamReportDto.deserialize(resultsDocument.toJson());
            } else {
                participantExamReportDto = new ParticipantExamReportDto(examNames);
            }
        }

        return participantExamReportDto;
    }

}

