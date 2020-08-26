package br.org.otus.survey.activity.sharing;

import br.org.otus.commons.FindByTokenService;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import br.org.otus.security.dtos.ParticipantTempTokenRequestDto;
import br.org.otus.security.services.TemporaryParticipantTokenService;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.sharing.ActivitySharingService;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.zip.DataFormatException;

public class ActivitySharingFacade {

  private static final String NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE = "Only autofill activities could generate url.";

  @Inject
  private ActivitySharingService activitySharingService;

  @Inject
  private ActivityService activityService;

  @Inject
  private ParticipantService participantService;

  @Inject
  private FindByTokenService findByTokenService;

  @Inject
  private TemporaryParticipantTokenService temporaryParticipantTokenService;


  public ActivitySharingDto getSharedURL(String activityId, String userToken) throws HttpResponseException {
    try {
      SurveyActivity surveyActivity = checkIfActivityModeIsAutoFill(activityId);
      Participant participant = participantService.getByRecruitmentNumber(surveyActivity.getParticipantData().getRecruitmentNumber());
      ObjectId userOID = findByTokenService.findUserByToken(userToken).get_id();
      String token = temporaryParticipantTokenService.generateTempToken(
        new ParticipantTempTokenRequestDto(participant.getRecruitmentNumber(), userOID.toString())
      );
      ActivitySharing activitySharing =  new ActivitySharing(surveyActivity.getActivityID(), userOID, token);
      String url = activitySharingService.getSharedURL(activitySharing);
      return new ActivitySharingDto(activitySharing, url);
    }
    catch (DataFormatException | DataNotFoundException | ValidationException | ParseException | TokenException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public String renovateSharedURL(String activityId) throws HttpResponseException {
    try {
      ActivitySharing activitySharing = new ActivitySharing(new ObjectId(activityId), null, null);
      return activitySharingService.renovateSharedURL(activitySharing);
    }
    catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public void deleteSharedURL(String activityId) throws HttpResponseException {
    try {
      activitySharingService.deleteSharedURL(activityId);
    }
    catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }


  private SurveyActivity checkIfActivityModeIsAutoFill(String activityID) throws DataNotFoundException, DataFormatException {
    SurveyActivity surveyActivity = activityService.getByID(activityID);
    if (!surveyActivity.getMode().name().equals(ActivityMode.AUTOFILL.toString())) {
      throw new DataFormatException(NOT_AUTOFILL_INVALID_SHARED_LINK_REQUEST_MESSAGE);
    }
    return surveyActivity;
  }

}
