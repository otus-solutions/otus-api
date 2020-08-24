package br.org.otus.survey.activity.sharing;

import br.org.otus.commons.FindByTokenService;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.sharing.ActivitySharingService;

import javax.inject.Inject;
import java.text.ParseException;

public class ActivitySharingFacade {

  private static final String AUTOFILL_MODE = "AUTOFILL";
  private static final String NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE = "Apenas atividades de autopreenchimento podem gerar link.";

  @Inject
  private ActivitySharingService activitySharingService;

  @Inject
  private ActivityService activityService;

  @Inject
  private ParticipantService participantService;

  @Inject
  private FindByTokenService findByTokenService;


  public String getSharedLink(String activityID, String userToken) throws HttpResponseException {
    try {
      SurveyActivity surveyActivity = checkIfActivityModeIsAutoFill(activityID);
      String url = activitySharingService.getSharedLink(activityID);
      if(url==null){
        url = createSharedLink(surveyActivity, userToken);
      }
      return url;
    } catch (ReadRequestException | RequestException | DataNotFoundException | ValidationException | ParseException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public String recreateSharedLink(String activityID, String userToken) throws HttpResponseException {
    try {
      SurveyActivity surveyActivity = checkIfActivityModeIsAutoFill(activityID);
      return createSharedLink(surveyActivity, userToken);
    } catch (ReadRequestException | RequestException | DataNotFoundException | ValidationException | ParseException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public void deleteSharedLink(String activityID) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      activitySharingService.deleteSharedLink(activityID);
    } catch (ReadRequestException | RequestException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }


  private SurveyActivity checkIfActivityModeIsAutoFill(String activityID) throws DataNotFoundException {
    SurveyActivity surveyActivity = activityService.getByID(activityID);
    if (!surveyActivity.getMode().name().equals(AUTOFILL_MODE)) {
      throw new HttpResponseException(Validation.build(NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE, null));
    }
    return surveyActivity;
  }

  private String createSharedLink(SurveyActivity surveyActivity, String userToken) throws DataNotFoundException, ValidationException, ParseException {
    ObjectId userOID = findByTokenService.findUserByToken(userToken).get_id();
    Participant participant = participantService.getByRecruitmentNumber(surveyActivity.getParticipantData().getRecruitmentNumber());
    ActivitySharing activitySharing = new ActivitySharing(
      surveyActivity.getActivityID(),
      userOID,
      generateTempParticipantToken(participant));
    return activitySharingService.recreateSharedLink(activitySharing);
  }

  private String generateTempParticipantToken(Participant participant){
    return "abc"; //todo
  }

}
