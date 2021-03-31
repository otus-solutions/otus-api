package br.org.otus.extraction;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import javax.inject.Inject;

import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.info.Validation;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.business.ParticipantExtraction;
import org.ccem.otus.participant.business.extraction.model.ParticipantResultExtraction;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptExtractionDTO;
import org.ccem.otus.service.DataSourceService;
import org.ccem.otus.service.extraction.ActivityProgressExtraction;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.participant.service.extraction.ParticipantContactAttemptsExtraction;
import org.ccem.otus.service.extraction.factories.ActivityProgressRecordsFactory;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;
import org.ccem.otus.service.extraction.preprocessing.AutocompleteQuestionPreProcessor;
import org.ccem.otus.survey.form.SurveyForm;

import br.org.otus.api.ExtractionService;
import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.extraction.ExamUploadExtration;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;
import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.laboratory.extraction.LaboratoryExtraction;
import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;
import br.org.otus.participant.api.ParticipantContactAttemptFacade;

public class ExtractionFacade {

  private final static Logger LOGGER = Logger.getLogger("br.org.otus.extraction.ExtractionFacade");

  @Inject
  private ActivityFacade activityFacade;
  @Inject
  private SurveyFacade surveyFacade;
  @Inject
  private ExamUploadFacade examUploadFacade;
  @Inject
  private ParticipantFacade participantFacade;
  @Inject
  private ParticipantContactAttemptFacade participantContactAttemptFacade;
  @Inject
  private AutocompleteQuestionPreProcessor autocompleteQuestionPreProcessor;
  @Inject
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Inject
  private FileUploaderFacade fileUploaderFacade;
  @Inject
  private ExtractionService extractionService;
  @Inject
  private DataSourceService dataSourceService;
  @Inject
  private LaboratoryConfigurationService laboratoryConfigurationService;

  public byte[] createActivityExtraction(String acronym, Integer version) {
    SurveyForm surveyForm = surveyFacade.get(acronym, version);
    List<SurveyActivity> activities = activityFacade.getExtraction(acronym, version);
    Map<Long, String> fieldCenterByRecruitmentNumber = activityFacade.getParticipantFieldCenterByActivity(acronym, version);

    dataSourceService.populateDataSourceMapping();
    SurveyActivityExtraction extractor = new SurveyActivityExtraction(surveyForm, activities, fieldCenterByRecruitmentNumber);
    extractor.addPreProcessor(autocompleteQuestionPreProcessor);

    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction {" + acronym + "} not found."));
    }
  }

  public List<Integer> listSurveyVersions(String acronym) {
    return surveyFacade.listVersions(acronym);
  }

  public byte[] createLaboratoryExamsValuesExtraction() {
    LinkedList<ParticipantExamUploadResultExtraction> records = examUploadFacade.getExamResultsExtractionValues();
    ExamUploadExtration extractor = new ExamUploadExtration(records);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction not found."));
    }
  }

  public byte[] createLaboratoryExtraction() {
    List<TubeCustomMetadata> tubeCustomMetadata = laboratoryConfigurationService.getTubeCustomMedataData();
    LinkedList<LaboratoryRecordExtraction> extraction = participantLaboratoryFacade.getLaboratoryExtraction();
    LaboratoryExtraction extractor = new LaboratoryExtraction(extraction, tubeCustomMetadata);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction not found."));
    }
  }

  public byte[] createParticipantExtraction() {
    LinkedList<ParticipantResultExtraction> extraction = participantFacade.getParticipantExtraction();
    ParticipantExtraction extractor = new ParticipantExtraction(extraction);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction not found."));
    }
  }

  public byte[] createParticipantContactAttemptsExtraction() {
    ArrayList<ParticipantContactAttemptExtractionDTO> participantContactAttempts = participantContactAttemptFacade.finParticipantContactAttempts();
    ParticipantContactAttemptsExtraction extractor = new ParticipantContactAttemptsExtraction(participantContactAttempts);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction not found."));
    }
  }

}
