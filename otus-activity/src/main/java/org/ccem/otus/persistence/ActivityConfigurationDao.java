package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;

import java.util.List;
import java.util.Optional;

public interface ActivityConfigurationDao {

    List<ActivityCategory> find();

    ActivityCategory findByName(String name) throws DataNotFoundException;

    Optional<ActivityCategory> getLastInsertedCategory();

    ActivityCategory create(ActivityCategory activityCategory);

    String delete(String name) throws DataNotFoundException;

    ActivityCategory update(ActivityCategory activityCategory) throws DataNotFoundException;

    String findDefault(String name) throws DataNotFoundException;
}
