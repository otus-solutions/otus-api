package br.org.otus.survey.activity.sharing;

import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.sharing.ActivitySharingService;

import javax.inject.Inject;

public class ActivitySharingFacade {

  private static final String AUTOFILL_MODE = "AUTOFILL";
  private static final String NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE = "Apenas atividades de atuopreenchimento podem gerar link.";

  @Inject
  private ActivitySharingService activitySharingService;

  public String getSharedLink(String activityID, String token) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      return activitySharingService.getSharedLink(activityID, token);

    } catch (ReadRequestException | RequestException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public String recreateSharedLink(String activityID, String token) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      return activitySharingService.recreateSharedLink(activityID, token);

    } catch (ReadRequestException | RequestException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public void deleteSharedLink(String activityID, String token) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      activitySharingService.deleteSharedLink(activityID, token);
    } catch (ReadRequestException | RequestException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  private void checkIfActivityModeIsAutoFill(String activityID){
    SurveyActivity surveyActivity = null;
//    if (!surveyActivity.getMode().name().equals(AUTOFILL_MODE)) {
//      throw new HttpResponseException(Validation.build(NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE, null));
//    }

  }
}
