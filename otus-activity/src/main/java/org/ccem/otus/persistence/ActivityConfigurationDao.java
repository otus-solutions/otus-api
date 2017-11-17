package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;

import java.util.List;

public interface ActivityConfigurationDao {

    List<ActivityCategory> find();

    ActivityCategory findByName(String name) throws DataNotFoundException;

    ActivityCategory getLastInsertedCategory();

    ActivityCategory create(ActivityCategory activityCategory);

    void delete(String name) throws DataNotFoundException;

    ActivityCategory update(ActivityCategory activityCategory) throws DataNotFoundException;

    ActivityCategory findDefault() throws DataNotFoundException;
}
