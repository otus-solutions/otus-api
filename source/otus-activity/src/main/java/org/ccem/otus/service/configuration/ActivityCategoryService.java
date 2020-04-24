package org.ccem.otus.service.configuration;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;

import java.util.List;

public interface ActivityCategoryService {

  List<ActivityCategory> list();

  ActivityCategory getByName(String name) throws DataNotFoundException;

  ActivityCategory create(ActivityCategory activityCategory);

  void delete(String name) throws DataNotFoundException;

  ActivityCategory update(ActivityCategory activityCategory) throws DataNotFoundException;

  void setDefaultCategory(String name) throws DataNotFoundException;

  ActivityCategory getDefault();
}
