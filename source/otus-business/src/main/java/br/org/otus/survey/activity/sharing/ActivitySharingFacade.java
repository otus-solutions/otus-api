package br.org.otus.survey.activity.sharing;

import br.org.otus.commons.FindByTokenService;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.sharing.ActivitySharingService;

import javax.inject.Inject;
import java.text.ParseException;

public class ActivitySharingFacade {

  private static final String NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE = "Apenas atividades de autopreenchimento podem gerar link.";

  @Inject
  private ActivitySharingService activitySharingService;

  @Inject
  private ActivityService activityService;

  @Inject
  private ParticipantService participantService;

  @Inject
  private FindByTokenService findByTokenService;


  public String getSharedURL(String activityID, String userToken) throws HttpResponseException {
    try {
      return activitySharingService.getSharedURL(buildActivitySharing(activityID, userToken));
    }
    catch (DataNotFoundException | ValidationException | ParseException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public String renovateSharedURL(String activityID, String userToken) throws HttpResponseException {
    try {
      return activitySharingService.renovateSharedURL(buildActivitySharing(activityID, userToken));
    }
    catch (DataNotFoundException | ValidationException | ParseException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public void deleteSharedURL(String activityID) throws HttpResponseException {
    try {
      checkIfActivityModeIsAutoFill(activityID);
      activitySharingService.deleteSharedURL(activityID);
    }
    catch (DataNotFoundException | ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }


  private SurveyActivity checkIfActivityModeIsAutoFill(String activityID) throws DataNotFoundException, ValidationException {
    SurveyActivity surveyActivity = activityService.getByID(activityID);
    if (!surveyActivity.getMode().name().equals(ActivityMode.AUTOFILL.toString())) {
      throw new ValidationException(Validation.build(NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE, null));
    }
    return surveyActivity;
  }

  private ActivitySharing buildActivitySharing(String activityID, String userToken) throws DataNotFoundException, ValidationException, ParseException {
    SurveyActivity surveyActivity = checkIfActivityModeIsAutoFill(activityID);
    Participant participant = participantService.getByRecruitmentNumber(surveyActivity.getParticipantData().getRecruitmentNumber());
    ObjectId userOID = findByTokenService.findUserByToken(userToken).get_id();
    return new ActivitySharing(
      surveyActivity.getActivityID(),
      userOID,
      generateTempParticipantToken(participant));
  }

  private String generateTempParticipantToken(Participant participant){
    return "abc"; //todo
  }

}
