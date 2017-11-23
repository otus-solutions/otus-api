package org.ccem.otus.service.configuration;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.persistence.ActivityConfigurationDao;
import org.ccem.otus.persistence.ActivityDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ActivityCategoryServiceBean implements ActivityCategoryService {

    @Inject
    ActivityConfigurationDao activityConfigurationDao;

    @Inject
    ActivityDao activityDao;

    @Override
    public List<ActivityCategory> list() {
        return activityConfigurationDao.findNonDeleted();
    }

    @Override
    public ActivityCategory getByName(String name) throws DataNotFoundException {
        return activityConfigurationDao.findByName(name);
    }

    @Override
    public ActivityCategory create(ActivityCategory activityCategory) {
        ActivityCategory lastInsertedCategory = activityConfigurationDao.getLastInsertedCategory();

        if (lastInsertedCategory != null) activityCategory.setName(lastInsertedCategory.getName());
        else {
            activityCategory.setName();
            activityCategory.setDefault(true);
        }

        return activityConfigurationDao.create(activityCategory);
    }

    @Override
    public void delete(String name) throws DataNotFoundException {
        if (activityDao.findByCategory(name).size() != 0){
            activityConfigurationDao.disable(name);
        }
        else {
            activityConfigurationDao.delete(name);
        }

    }

    @Override
    public ActivityCategory update(ActivityCategory activityCategory) throws DataNotFoundException {
        return activityConfigurationDao.update(activityCategory);
    }

    @Override
    public void setDefaultCategory(String name) throws DataNotFoundException {
        activityConfigurationDao.setNewDefault(name);
    }
}
