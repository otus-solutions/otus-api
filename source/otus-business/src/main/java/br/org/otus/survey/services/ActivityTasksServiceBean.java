package br.org.otus.survey.services;

import br.org.otus.extraction.ActivityExtractionFacade;
import br.org.otus.outcomes.FollowUpFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import br.org.otus.user.management.ManagementUserService;
import com.nimbusds.jwt.SignedJWT;
import org.bson.types.ObjectId;
import org.ccem.otus.enums.AuthenticationMode;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.UserNotFoundException;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.sharing.ActivitySharingService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Stateless
public class ActivityTasksServiceBean implements ActivityTasksService {

  private final static Logger LOGGER = Logger.getLogger("br.org.otus.survey.services.ActivityTasksServiceBean");

  @Inject
  private ActivityService activityService;

  @Inject
  private ParticipantService participantService;

  @Inject
  private ManagementUserService managementUserService;

  @Inject
  private FollowUpFacade followUpFacade;

  @Inject
  private ActivitySharingService activitySharingService;

  @Inject
  private ActivityExtractionFacade extractionFacade;


  @Override
  public String create(SurveyActivity surveyActivity, boolean notify) {
    String activityId = activityService.create(surveyActivity);
    surveyActivity.setActivityID(new ObjectId(activityId));

    if (surveyActivity.getMode() == ActivityMode.AUTOFILL) {
      followUpFacade.createParticipantActivityAutoFillEvent(surveyActivity, notify);
    }

    CompletableFuture.runAsync(() -> {
      try{
        extractionFacade.createOrUpdateActivityExtraction(surveyActivity.getActivityID().toString());
      }
      catch (Exception e){
        LOGGER.severe("status: fail, action: create activity extraction for activityId " + surveyActivity.getActivityID().toString());
        new Exception("Error while syncing results", e).printStackTrace();
      }
    });

    return activityId;
  }

  @Override
  public SurveyActivity updateActivity(SurveyActivity surveyActivity, String token) throws ParseException, DataNotFoundException, HttpResponseException {
    updateStatusHistoryUser(surveyActivity, generateStatusHistoryUserForUpdate(token));

    SurveyActivity updatedActivity = activityService.update(surveyActivity);

    if (updatedActivity.getMode() == ActivityMode.AUTOFILL) {
      updateAutofillActivity(updatedActivity);
    }

    CompletableFuture.runAsync(() -> {
      String action = "update";
      try{
        if(surveyActivity.isDiscarded()){
          action = "delete";
          extractionFacade.deleteActivityExtraction(surveyActivity.getActivityID().toString());
        }
        else{
          extractionFacade.createOrUpdateActivityExtraction(surveyActivity.getActivityID().toString());
        }
      }
      catch (Exception e){
        LOGGER.severe("status: fail, action: " + action + " activity extraction for activityId " + surveyActivity.getActivityID().toString());
        new Exception("Error while syncing results", e).printStackTrace();
      }
    });

    return updatedActivity;
  }

  @Override
  public void save(String userEmail, OfflineActivityCollection offlineActivityCollection) throws DataNotFoundException {
    activityService.save(userEmail, offlineActivityCollection);

    CompletableFuture.runAsync(() -> {
      offlineActivityCollection.getActivities().forEach(surveyActivity -> {
        try{
          extractionFacade.createOrUpdateActivityExtraction(surveyActivity.getActivityID().toString());
        }
        catch (Exception e){
          LOGGER.severe("status: fail, action: save activity extraction for activityId " + surveyActivity.getActivityID().toString() +
            " from offlineActivityCollection " + offlineActivityCollection.get_id());
          new Exception("Error while syncing results", e).printStackTrace();
        }
      });
    });
  }

  @Override
  public void discardById(String activityId) throws DataNotFoundException {
    activityService.discardByID(new ObjectId(activityId));

    CompletableFuture.runAsync(() -> {
      try{
        extractionFacade.deleteActivityExtraction(activityId);
      }
      catch (Exception e){
        LOGGER.severe("status: fail, action: delete activity extraction for activityId " + activityId);
        new Exception("Error while syncing results", e).printStackTrace();
      }
    });
  }

  private User generateStatusHistoryUserForUpdate(String token) throws ParseException, DataNotFoundException, HttpResponseException {
    Participant participant = null;
    token = token.substring("Bearer".length()).trim();
    SignedJWT signedJWT = SignedJWT.parse(token);
    String mode = signedJWT.getJWTClaimsSet().getClaim("mode").toString();
    Object email = signedJWT.getJWTClaimsSet().getClaim("iss");

    switch (AuthenticationMode.valueFromName(mode)) {
      case USER:
        br.org.otus.model.User user = managementUserService.fetchByEmail(email.toString());
        return new User(user.getName(), user.getEmail(), user.getSurname(), user.getPhone());

      case PARTICIPANT:
        participant = participantService.getByEmail(email.toString());
        return new User(participant.getName(), participant.getEmail(), "", null);

      case ACTIVITY_SHARING:
        Long rn = Long.parseLong(signedJWT.getJWTClaimsSet().getClaim("recruitmentNumber").toString());
        participant = participantService.getByRecruitmentNumber(rn);
        return new User(participant.getName(), participant.getEmail(), "", null);

      default:
        throw new HttpResponseException(Validation.build("Invalid token mode", null));
    }
  }

  private void updateStatusHistoryUser(SurveyActivity updatedActivity, User statusHistoryUser){
    List<ActivityStatus> statusHistory = updatedActivity.getStatusHistory();
    int i = statusHistory.size() - 1;
    while (i != 0){
      ActivityStatus activityStatus = statusHistory.get(i);
      try {
        activityStatus.getUser();
        i = 0;
      } catch (UserNotFoundException e) {
        activityStatus.setUser(statusHistoryUser);
        i--;
      }
    }
  }

  private void updateAutofillActivity(SurveyActivity surveyActivity) throws DataNotFoundException {
    String activityId = String.valueOf(surveyActivity.getActivityID());
    if(surveyActivity.isDiscarded()){
      followUpFacade.cancelParticipantEventByActivityId(activityId);

      ObjectId activitySharingId = activitySharingService.getActivitySharingIdByActivityId(surveyActivity.getActivityID());
      if(Objects.nonNull(activitySharingId)) {
        activitySharingService.deleteSharedURL(String.valueOf(activitySharingId));
      }
    }
    else{
      String nameLastStatusHistory = surveyActivity.getLastStatus().get().getName();
      followUpFacade.statusUpdateEvent(nameLastStatusHistory, activityId);
    }
  }

}
