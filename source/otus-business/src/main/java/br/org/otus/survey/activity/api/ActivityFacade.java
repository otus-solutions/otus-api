package br.org.otus.survey.activity.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

import com.google.gson.JsonSyntaxException;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;

public class ActivityFacade {

  @Inject
  private ActivityService activityService;

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
