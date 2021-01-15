package br.org.otus.extraction;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import javax.inject.Inject;

import br.org.otus.api.CsvExtraction;
import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.info.Validation;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.service.DataSourceService;
import org.ccem.otus.service.extraction.ActivityProgressExtraction;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.service.extraction.factories.ActivityProgressRecordsFactory;
import org.ccem.otus.service.extraction.model.ActivityExtraction;
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

public class ExtractionFacade {

  private final static Logger LOGGER = Logger.getLogger("br.org.otus.extraction.ExtractionFacade");

  @Inject
  private ActivityFacade activityFacade;
  @Inject
  private SurveyFacade surveyFacade;
  @Inject
  private ExamUploadFacade examUploadFacade;
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
  private ParticipantFacade participantFacade;


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

  public ArrayList<LinkedTreeMap> createJsonExtractionFromPipeline(String pipelineName) {
    try {
      GatewayResponse gatewayResponse = new ExtractionGatewayService().getPipelineJsonExtraction(pipelineName);
      ArrayList<LinkedTreeMap> response = new GsonBuilder().create().fromJson(
        (String) gatewayResponse.getData(), ArrayList.class);
      LOGGER.info("status: success, action: extraction for pipeline " + pipelineName + " as json");
      return response;
    } catch (IOException e) {
      LOGGER.severe("status: fail, action: extraction for pipeline " + pipelineName + " as json");
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public byte[] createCsvExtractionFromPipeline(String pipelineName) {
    try {
      GatewayResponse gatewayResponse = new ExtractionGatewayService().getPipelineCsvJsonExtraction(pipelineName);
      byte[] csv = extractionService.createExtraction(new CsvExtraction((String) gatewayResponse.getData()));
      LOGGER.info("status: success, action: extraction for pipeline " + pipelineName + " as csv");
      return csv;
    } catch (IOException | DataNotFoundException e) {
      LOGGER.severe("status: fail, action: extraction for pipeline " + pipelineName + " as csv");
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void createOrUpdateActivityExtraction(String activityId) throws HttpResponseException {
    try {
      new ExtractionGatewayService().createOrUpdateActivityExtraction(getActivityExtraction(activityId).toJson());
      LOGGER.info("status: success, action: create/update extraction for activity " + activityId);
    }
    catch (ValidationException | IOException e) {
      LOGGER.severe("status: fail, action: create/update extraction for activity " + activityId);
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void deleteActivityExtraction(String activityId) {
    try {
      ActivityExtraction activityExtraction = getActivityExtraction(activityId);
      new ExtractionGatewayService().deleteActivityExtraction(
        activityExtraction.getSurveyData().getId(),
        activityExtraction.getActivityData().getId()
      );
      LOGGER.info("status: success, action: delete extraction for activity " + activityId);
    }
    catch (ValidationException | IOException e) {
      LOGGER.severe("status: fail, action: delete extraction for activity " + activityId);
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  private ActivityExtraction getActivityExtraction(String activityId) throws ValidationException {
    SurveyActivity surveyActivity = activityFacade.getByID(activityId);
    if(surveyActivity.isDiscarded()){
      throw new ValidationException("Activity " + activityId + " is discarded");
    }
    if(!surveyActivity.couldBeExtracted()){
      throw new ValidationException("Activity " + activityId + " could not be extracted");
    }
    SurveyForm surveyForm = surveyFacade.get(surveyActivity.getSurveyForm().getAcronym(), surveyActivity.getSurveyForm().getVersion());
    Participant participant = participantFacade.getByRecruitmentNumber(surveyActivity.getParticipantData().getRecruitmentNumber());
    return new ActivityExtraction(surveyForm, surveyActivity, participant);
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
    LinkedList<LaboratoryRecordExtraction> extraction = participantLaboratoryFacade.getLaboratoryExtraction();
    LaboratoryExtraction extractor = new LaboratoryExtraction(extraction);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction not found."));
    }
  }

  public byte[] createAttachmentsReportExtraction(String acronym, Integer version) {
    try {
      return extractionService.getAttachmentsReport(acronym, version);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public byte[] createActivityProgressExtraction(String center) {
    LinkedList<ActivityProgressResultExtraction> progress = activityFacade.getActivityProgressExtraction(center);
    ActivityProgressRecordsFactory extraction = new ActivityProgressRecordsFactory(progress);
    ActivityProgressExtraction extractor = new ActivityProgressExtraction(extraction);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public byte[] downloadFiles(ArrayList<String> oids) {
    return fileUploaderFacade.downloadFiles(oids);
  }

}
