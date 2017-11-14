package org.ccem.otus.service.configuration;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;

import java.util.List;

public interface ActivityCategoryService {

    List<ActivityCategory> list();

    ActivityCategory getByName(String name) throws DataNotFoundException;

    String create(ActivityCategory activityCategory);

    String delete(String name) throws DataNotFoundException;

    String update(ActivityCategory activityCategory) throws DataNotFoundException;

    ActivityCategory setDefault(String name) throws DataNotFoundException;
}
