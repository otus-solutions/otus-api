package br.org.otus.extraction;

import br.org.otus.api.CsvExtraction;
import br.org.otus.api.ExtractionService;
import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.service.extraction.model.ActivityExtraction;
import org.ccem.otus.service.extraction.model.Pipeline;
import org.ccem.otus.service.extraction.model.PipelineDto;
import org.ccem.otus.survey.form.SurveyForm;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ActivityExtractionFacade {

  private final static Logger LOGGER = Logger.getLogger("br.org.otus.extraction.ActivityExtractionFacade");

  @Inject
  private ActivityFacade activityFacade;
  @Inject
  private SurveyFacade surveyFacade;
  @Inject
  private ExtractionService extractionService;
  @Inject
  private ParticipantFacade participantFacade;


  public byte[] getSurveyActivitiesExtractionAsCsv(String acronym, Integer version) {
    try {
      String surveyId = surveyFacade.get(acronym, version).getSurveyID().toHexString();
      GatewayResponse gatewayResponse = new ExtractionGatewayService().getCsvSurveyExtraction(surveyId);
      byte[] csv = extractionService.createExtraction(new CsvExtraction((String) gatewayResponse.getData()));
      LOGGER.info("status: success, action: extraction for survey {" + acronym + ", version " + version + "} as csv");
      return csv;
    } catch (IOException | DataNotFoundException e) {
      LOGGER.severe("status: fail, action: extraction for survey {" + acronym + ", version " + version + "} as csv");
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public ArrayList<LinkedTreeMap> getSurveyActivitiesExtractionAsJson(String acronym, Integer version) {
    try {
      String surveyId = surveyFacade.get(acronym, version).getSurveyID().toHexString();
      GatewayResponse gatewayResponse = new ExtractionGatewayService().getJsonSurveyExtraction(surveyId);
      ArrayList<LinkedTreeMap> response = new GsonBuilder().create().fromJson(
        (String) gatewayResponse.getData(), ArrayList.class);
      LOGGER.info("status: success, action: extraction for survey {" + acronym + ", version " + version + "} as json");
      return response;
    } catch (IOException e) {
      LOGGER.severe("status: fail, action: extraction for for survey {" + acronym + ", version " + version + "} as json");
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  private String buildPipeline(String pipelineDtoJson){
    PipelineDto pipelineDto = PipelineDto.fromJson(pipelineDtoJson);
    SurveyForm surveyForm = surveyFacade.get(pipelineDto.getSurveyForm().getAcronym(), pipelineDto.getSurveyForm().getVersion());
    return new Pipeline(surveyForm.getSurveyID().toHexString(), pipelineDto.getRscript()).toJson();
  }

  public void createOrUpdateActivityExtraction(String activityId) throws HttpResponseException {
    try {
      new ExtractionGatewayService().createOrUpdateActivityExtraction(buildActivityExtractionModelForCreateOrUpdate(activityId).toJson());
      LOGGER.info("status: success, action: create/update extraction for activity " + activityId);
    }
    catch (ValidationException | IOException e) {
      LOGGER.severe("status: fail, action: create/update extraction for activity " + activityId);
      String message = (e.getCause()!=null ? e.getCause().getMessage() : e.getMessage());
      throw new HttpResponseException(Validation.build(message));
    }
  }

  public void deleteActivityExtraction(String activityId) {
    try {
      ActivityExtraction activityExtraction = buildActivityExtractionModel(activityId);
      new ExtractionGatewayService().deleteActivityExtraction(
        activityExtraction.getSurveyData().getId(),
        activityExtraction.getActivityData().getId()
      );
      LOGGER.info("status: success, action: DELETE extraction for activity " + activityId);
    }
    catch (ValidationException | IOException e) {
      LOGGER.severe("status: fail, action: DELETE extraction for activity " + activityId);
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  private ActivityExtraction buildActivityExtractionModel(String activityId) throws ValidationException {
    SurveyActivity surveyActivity = activityFacade.getByID(activityId);
    if(surveyActivity.isDiscarded()){
      throw new ValidationException(new Throwable("Activity " + activityId + " is discarded"));
    }
    if(!surveyActivity.couldBeExtracted()){
      throw new ValidationException(new Throwable("Activity " + activityId + " could not be extracted"));
    }
    SurveyForm surveyForm = surveyFacade.get(surveyActivity.getSurveyForm().getAcronym(), surveyActivity.getSurveyForm().getVersion());
    return new ActivityExtraction(surveyForm, surveyActivity);
  }

  private ActivityExtraction buildActivityExtractionModelForCreateOrUpdate(String activityId) throws ValidationException {
    ActivityExtraction activityExtraction = buildActivityExtractionModel(activityId);
    Participant participant = participantFacade.getByRecruitmentNumber(activityExtraction.getActivityData().getRecruitmentNumber());
    activityExtraction.setParticipantData(participant);
    return activityExtraction;
  }

}
