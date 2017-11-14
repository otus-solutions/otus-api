package org.ccem.otus.service.configuration;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;

import java.util.List;

public class ActivityCategoryServiceBean implements ActivityCategoryService {
    @Override
    public List<ActivityCategory> list() {
        return null;
    }

    @Override
    public ActivityCategory getByName(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public String create(ActivityCategory activityCategory) {
        return null;
    }

    @Override
    public String delete(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public String update(ActivityCategory activityCategory) throws DataNotFoundException {
        return null;
    }

    @Override
    public String setDefaultCategory(String name) throws DataNotFoundException {
        return null;
    }
}
