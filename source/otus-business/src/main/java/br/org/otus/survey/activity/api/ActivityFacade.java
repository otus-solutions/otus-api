package br.org.otus.survey.activity.api;

import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.outcomes.FollowUpFacade;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import br.org.otus.user.management.ManagementUserService;
import com.google.gson.JsonSyntaxException;
import com.nimbusds.jwt.SignedJWT;
import org.bson.types.ObjectId;
import org.ccem.otus.enums.AuthenticationMode;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.UserNotFoundException;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollectionGroupsDTO;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.configuration.ActivityCategoryService;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ActivityFacade {
  @Inject
  private ActivityService activityService;

  @Inject
  private ParticipantService participantService;

  @Inject
  private ManagementUserService managementUserService;

  @Inject
  private ActivityCategoryService activityCategoryService;

  @Inject
  private FollowUpFacade followUpFacade;
  private SurveyActivity activityUpdated;

  public List<SurveyActivity> list(long rn, String userEmail) {
    return activityService.list(rn, userEmail);
  }

  public SurveyActivity getByID(String id) {
    try {
      return activityService.getByID(id);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public List<SurveyActivity> get(String acronym, Integer version) {
    try {
      return activityService.get(acronym, version);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (MemoryExcededException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public List<SurveyActivity> getExtraction(String acronym, Integer version) {
    try {
      return activityService.getExtraction(acronym, version);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (MemoryExcededException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public String create(SurveyActivity surveyActivity, boolean notify) {
    try {
      isMissingRequiredExternalID(surveyActivity);
      String activityId = activityService.create(surveyActivity);

      surveyActivity.setActivityID(new ObjectId(activityId));
      boolean demandsParticipantEvent = checkForParticipantEventCreation(surveyActivity);
      if (demandsParticipantEvent) {
        followUpFacade.createParticipantActivityAutoFillEvent(surveyActivity, notify);
      }

      return activityId;

    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  private boolean checkForParticipantEventCreation(SurveyActivity surveyActivity) {
    return (surveyActivity.getMode() != null && surveyActivity.getMode() == ActivityMode.AUTOFILL);
  }

  public SurveyActivity updateActivity(SurveyActivity surveyActivity, String token) {
    try {
      User statusHistoryUser = null;
      Participant participant = null;
      token = token.substring("Bearer".length()).trim();
      SignedJWT signedJWT = SignedJWT.parse(token);
      String mode = signedJWT.getJWTClaimsSet().getClaim("mode").toString();
      Object email = signedJWT.getJWTClaimsSet().getClaim("iss");

      switch (AuthenticationMode.valueFromName(mode)) {
        case USER:
          br.org.otus.model.User user = managementUserService.fetchByEmail(email.toString());
          statusHistoryUser = new User(user.getName(), user.getEmail(), user.getSurname(), user.getPhone());
          break;

        case PARTICIPANT:
          participant = participantService.getByEmail(email.toString());
          statusHistoryUser = new User(participant.getName(), participant.getEmail(), "", null);
          break;

        case ACTIVITY_SHARING:
          Long rn = Long.parseLong(signedJWT.getJWTClaimsSet().getClaim("recruitmentNumber").toString());
          participant = participantService.getByRecruitmentNumber(rn);
          statusHistoryUser = new User(participant.getName(), participant.getEmail(), "", null);
          break;

        default:
          throw new HttpResponseException(Validation.build("Invalid token mode", null));
      }

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

      activityUpdated = activityService.update(surveyActivity);

      if (activityUpdated.getMode().name().equals(ActivityMode.AUTOFILL.name())) {
        String nameLastStatusHistory = activityUpdated.getLastStatus().get().getName();
        String activityId = String.valueOf(activityUpdated.getActivityID());
        followUpFacade.statusUpdateEvent(nameLastStatusHistory, activityId);
      }
      return activityUpdated;

    } catch (DataNotFoundException | ParseException e) {
      throw new HttpResponseException(Validation.build(e.getMessage(), e.getCause()));
    }
  }

  public boolean updateCheckerActivity(String checkerUpdated) {
    try {
      return activityService.updateCheckerActivity(checkerUpdated);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage(), e.getData()));
    }
  }

  public LinkedList<ActivityProgressResultExtraction> getActivityProgressExtraction(String center) {
    try {
      return activityService.getActivityProgressExtraction(center);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public HashMap<Long, String> getParticipantFieldCenterByActivity(String acronym, Integer version) {
    try {
      return activityService.getParticipantFieldCenterByActivity(acronym, version);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  @SuppressWarnings("static-access")
  public SurveyActivity deserialize(String surveyActivity) {
    try {
      return SurveyActivity.deserialize(surveyActivity);
    } catch (JsonSyntaxException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  private void isMissingRequiredExternalID(SurveyActivity surveyActivity) throws ValidationException {
    if (surveyActivity.getExternalID() == null && surveyActivity.hasRequiredExternalID()) {
      throw new ValidationException(new Throwable("Missing external ID required"));
    }
  }

  public void synchronize(long rn, String offlineCollectionId, br.org.otus.model.User user) {
    try {
      User statusHistoryUser = new User(user.getName(), user.getEmail(), user.getSurname(), user.getPhone());
      Participant participant = participantService.getByRecruitmentNumber(rn);
      participant.setTokenList(new ArrayList<>());
      participant.setPassword(null);
      ActivityCategory activityCategory = activityCategoryService.getDefault();
      OfflineActivityCollection offlineActivityCollection = activityService.fetchOfflineActivityCollection(offlineCollectionId);
      if (!offlineActivityCollection.getAvailableToSynchronize()) {
        throw new HttpResponseException(Validation.build("Offline collection is already synchronized"));
      } else if (!offlineActivityCollection.getUserId().equals(user.get_id())) {
        throw new HttpResponseException(Validation.build("Offline collection does not belong to you"));
      } else {
        List<ObjectId> createdActivityIds = new ArrayList<>();
        offlineActivityCollection.getActivities().forEach(activity -> {
          activity.setParticipantData(participant);
          activity.setCategory(activityCategory);
          activity.setStatusHistory(activity.getStatusHistory().stream().map(activityStatus -> {
            activityStatus.setUser(statusHistoryUser);
            return activityStatus;
          }).collect(Collectors.toList()));
          String createdId = activityService.create(activity);
          createdActivityIds.add(new ObjectId(createdId));
        });
        activityService.deactivateOfflineActivityCollection(offlineCollectionId, createdActivityIds);
      }
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public void save(String userEmail, OfflineActivityCollection offlineActivityCollection) {
    try {
      activityService.save(userEmail, offlineActivityCollection);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public OfflineActivityCollectionGroupsDTO fetchOfflineActivityCollections(String userEmail) {
    try {
      return activityService.fetchOfflineActivityCollections(userEmail);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public String createFollowUp(SurveyActivity surveyActivity) {
    try {
      isMissingRequiredExternalID(surveyActivity);
      return activityService.create(surveyActivity);

    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }
}
