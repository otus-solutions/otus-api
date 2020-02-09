package br.org.otus.survey.activity.configuration;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.service.configuration.ActivityCategoryService;
import org.json.JSONException;
import org.json.JSONObject;


import javax.inject.Inject;
import java.util.List;

public class ActivityCategoryFacade {

  @Inject
  private ActivityCategoryService activityCategoryService;

  public List<ActivityCategory> list() {
    return activityCategoryService.list();
  }

  public ActivityCategory getByName(String name) {
    try {
      return activityCategoryService.getByName(name);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public ActivityCategory create(String categoryLabelJson) {
    try {
      JSONObject categoryLabel = new JSONObject(categoryLabelJson);
      return activityCategoryService.create(new ActivityCategory((String) categoryLabel.get("label")));
    } catch (JSONException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void delete(String name) {
    try {
      activityCategoryService.delete(name);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void update(String activityCategoryJson) {
    try {
      activityCategoryService.update(ActivityCategory.deserialize(activityCategoryJson));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void setDefaultCategory(String name) {
    try {
      activityCategoryService.setDefaultCategory(name);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }
}
