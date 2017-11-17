package org.ccem.otus.service.configuration;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.persistence.ActivityConfigurationDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ActivityCategoryServiceBean implements ActivityCategoryService {

    @Inject
    ActivityConfigurationDao activityConfigurationDao;

    @Override
    public List<ActivityCategory> list() {
        return activityConfigurationDao.find();
    }

    @Override
    public ActivityCategory getByName(String name) throws DataNotFoundException {
        return activityConfigurationDao.findByName(name);
    }

    @Override
    public ActivityCategory create(ActivityCategory activityCategory) {
        ActivityCategory lastInsertedCategory = activityConfigurationDao.getLastInsertedCategory();

        if (lastInsertedCategory != null) activityCategory.setName(lastInsertedCategory.getName());
        else activityCategory.setName();

        return activityConfigurationDao.create(activityCategory);
    }

    @Override
    public void delete(String name) throws DataNotFoundException {
        activityConfigurationDao.delete(name);
    }

    @Override
    public ActivityCategory update(ActivityCategory activityCategory) throws DataNotFoundException {
        return activityConfigurationDao.update(activityCategory);
    }

    @Override
    public String setDefaultCategory(String name) throws DataNotFoundException {
        //TODO 17/11/17: findOneAndUpdate??
        ActivityCategory newDefault = activityConfigurationDao.findByName(name);
        ActivityCategory oldDefault = activityConfigurationDao.findDefault();
        return null;
    }
}
