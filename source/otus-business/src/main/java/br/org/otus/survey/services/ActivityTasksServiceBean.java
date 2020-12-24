package br.org.otus.survey.services;

import br.org.otus.extraction.ExtractionFacade;
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
import org.ccem.otus.service.configuration.ActivityCategoryService;
import org.ccem.otus.service.sharing.ActivitySharingService;
import service.StageService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Stateless
public class ActivityTasksServiceBean implements ActivityTasksService {

  private final static Logger LOGGER = Logger.getLogger("br.org.otus.extraction.ExtractionFacade");

  @Inject
  private ActivityService activityService;

  @Inject
  private ParticipantService participantService;

  @Inject
  private ManagementUserService managementUserService;

  @Inject
  private ActivityCategoryService activityCategoryService;

  @Inject
  private StageService stageService;

  @Inject
  private FollowUpFacade followUpFacade;

  @Inject
  private ActivitySharingService activitySharingService;

  @Inject
  private SurveyService surveyService;

  @Inject
  private ExtractionFacade extractionFacade;


  private SurveyActivity currActivity;

  @Override
  public String create(SurveyActivity surveyActivity, boolean notify) {
    String activityId = activityService.create(surveyActivity);
    surveyActivity.setActivityID(new ObjectId(activityId));

    if (isSurveyActivityAutofill(surveyActivity.getMode())) {
      followUpFacade.createParticipantActivityAutoFillEvent(surveyActivity, notify);
    }

    CompletableFuture.runAsync(() -> {
      try{
        extractionFacade.createActivityExtraction(surveyActivity.getActivityID().toString());
      }
      catch (Exception e){
        LOGGER.severe("status: fail, action: create activity extraction for activityId " + surveyActivity.getActivityID().toString());
        new Exception("Error while syncing results", e).printStackTrace();
      }
    });

    return activityId;
  }

  @Override
  public SurveyActivity updateActivity(SurveyActivity surveyActivity, String token) throws ParseException, DataNotFoundException {
    updateStatusHistoryUser(surveyActivity, generateStatusHistoryUserForUpdate(token));

    currActivity = activityService.update(surveyActivity);

    if (isSurveyActivityAutofill(currActivity.getMode())) {
      updateAutofillActivity();
    }

    CompletableFuture.runAsync(() -> {
      try{
        extractionFacade.updateActivityExtraction(surveyActivity.getActivityID().toString());
      }
      catch (Exception e){
        LOGGER.severe("status: fail, action: update activity extraction for activityId " + surveyActivity.getActivityID().toString());
        new Exception("Error while syncing results", e).printStackTrace();
      }
    });

    return currActivity;
  }

  @Override
  public void save(String userEmail, OfflineActivityCollection offlineActivityCollection) throws DataNotFoundException {
    activityService.save(userEmail, offlineActivityCollection);

    CompletableFuture.runAsync(() -> {
      offlineActivityCollection.getActivities().forEach(surveyActivity -> {
        try{
          extractionFacade.updateActivityExtraction(surveyActivity.getActivityID().toString());
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

  private boolean isSurveyActivityAutofill(ActivityMode activityMode) {
    return (activityMode != null && activityMode == ActivityMode.AUTOFILL);
  }

  private User generateStatusHistoryUserForUpdate(String token) throws ParseException, DataNotFoundException {
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

  private void updateStatusHistoryUser(SurveyActivity surveyActivity, User statusHistoryUser){
    List<ActivityStatus> statusHistory = surveyActivity.getStatusHistory();
    int size = statusHistory.size();
    for (int i = size - 1; i != 0; i--) {
      ActivityStatus activityStatus = statusHistory.get(i);
      try {
        activityStatus.getUser();
        break;
      } catch (UserNotFoundException e) {
        activityStatus.setUser(statusHistoryUser);
      }
    }
  }

  private void updateAutofillActivity() throws DataNotFoundException {
    String activityId = String.valueOf(currActivity.getActivityID());
    if(currActivity.isDiscarded()){
      followUpFacade.cancelParticipantEventByActivityId(activityId);

      ObjectId activitySharingId = activitySharingService.getActivitySharingIdByActivityId(currActivity.getActivityID());
      if(Objects.nonNull(activitySharingId)) {
        activitySharingService.deleteSharedURL(String.valueOf(activitySharingId));
      }
    }
    else{
      String nameLastStatusHistory = currActivity.getLastStatus().get().getName();
      followUpFacade.statusUpdateEvent(nameLastStatusHistory, activityId);
    }
  }

}
