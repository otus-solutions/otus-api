package br.org.otus.survey.activity.configuration;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import com.google.gson.GsonBuilder;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.model.survey.activity.configuration.ActivityConfiguration;
import org.ccem.otus.service.configuration.ActivityCategoryService;

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

    public String create(String categoryLabel) {
        ActivityCategory insertedCategory = activityCategoryService.create(new ActivityCategory(categoryLabel));
        return ActivityCategory.serialize(insertedCategory);
    }

    //TODO 14/11/17: implement
    public String createMany(){
        return null;
    }

    public String delete(String name) {
        try {
            return activityCategoryService.delete(name);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public String update(String activityCategoryJson){
        try {
            return activityCategoryService.update(ActivityCategory.deserialize(activityCategoryJson));
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public String setDefaultCategory(String name){
        try {
            return activityCategoryService.setDefaultCategory(name);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }
}
