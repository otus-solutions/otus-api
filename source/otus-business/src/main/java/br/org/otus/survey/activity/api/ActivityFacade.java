package br.org.otus.survey.activity.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.org.otus.user.management.ManagementUserService;
import com.nimbusds.jwt.SignedJWT;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

import com.google.gson.JsonSyntaxException;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;

public class ActivityFacade {

  @Inject
  private ActivityService activityService;

  @Inject
  private ParticipantService participantService;

  @Inject
  private ManagementUserService managementUserService;

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

  public String create(SurveyActivity surveyActivity) {
    try {
      isMissingRequiredExternalID(surveyActivity);
      return activityService.create(surveyActivity);

    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public SurveyActivity updateActivity(SurveyActivity surveyActivity) {
    try {
      return activityService.update(surveyActivity);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public SurveyActivity updateActivity(SurveyActivity surveyActivity, String token) {
    try {
      User statusHistoryUser;
      token = token.substring("Bearer".length()).trim();
      SignedJWT signedJWT = SignedJWT.parse(token);
      String mode = signedJWT.getJWTClaimsSet().getClaim("mode").toString();
      String email = signedJWT.getJWTClaimsSet().getClaim("iss").toString();

      if (mode.equals("user")) {
        br.org.otus.model.User user = managementUserService.fetchByEmail(email);
        statusHistoryUser = new User(user.getName(), user.getEmail(), user.getSurname(), user.getPhone());
        surveyActivity.getLastStatus().ifPresent(lastActivityStatus -> lastActivityStatus.setUser(statusHistoryUser));
      } else {
        Participant participant = participantService.getByEmail(email);
        statusHistoryUser = new User(participant.getName(), participant.getEmail(), "", null);
        surveyActivity.setStatusHistory(surveyActivity.getStatusHistory().stream().map(activityStatus -> {
          activityStatus.setUser(statusHistoryUser);
          return activityStatus;
        }).collect(Collectors.toList()));

        surveyActivity.getStatusHistory().forEach(activityStatus -> activityStatus.setUser(statusHistoryUser));

      }
      return activityService.update(surveyActivity);
    } catch (DataNotFoundException | ParseException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
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

}
