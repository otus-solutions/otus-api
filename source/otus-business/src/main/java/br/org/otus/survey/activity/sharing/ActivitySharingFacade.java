package br.org.otus.survey.activity.sharing;

import br.org.otus.extraction.ExtractionSecurityDaoBean;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.sharing.ActivitySharingService;

import javax.inject.Inject;

public class ActivitySharingFacade {

  private static final String AUTOFILL_MODE = "AUTOFILL";
  private static final String NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE = "Apenas atividades de autopreenchimento podem gerar link.";

  @Inject
  private ActivitySharingService activitySharingService;

  @Inject
  private ActivityService activityService;

  @Inject
  private ExtractionSecurityDaoBean extractionSecurityDaoBean;

  public String getSharedLink(String activityID, String token) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      String url = activitySharingService.getSharedLink(activityID);
      if(url==null){
        url = createSharedLink(activityID, token);
      }
      return url;
    } catch (ReadRequestException | RequestException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public String recreateSharedLink(String activityID, String token) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      return createSharedLink(activityID, token);
    } catch (ReadRequestException | RequestException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  private String createSharedLink(String activityID, String token) throws DataNotFoundException {
    User user = extractionSecurityDaoBean.validateSecurityCredentials(token);
    ActivitySharing activitySharing = new ActivitySharing(new ObjectId(activityID), token, user.getEmail());
    return activitySharingService.recreateSharedLink(activitySharing);
  }

  public void deleteSharedLink(String activityID) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      activitySharingService.deleteSharedLink(activityID);
    } catch (ReadRequestException | RequestException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  private void checkIfActivityModeIsAutoFill(String activityID) throws DataNotFoundException {
    SurveyActivity surveyActivity = activityService.getByID(activityID);
    if (!surveyActivity.getMode().name().equals(AUTOFILL_MODE)) {
      throw new HttpResponseException(Validation.build(NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE, null));
    }
  }

}
