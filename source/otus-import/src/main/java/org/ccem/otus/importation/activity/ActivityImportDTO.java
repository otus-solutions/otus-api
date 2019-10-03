package org.ccem.otus.importation.activity;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import java.util.List;

public class ActivityImportDTO {
    private List<SurveyActivity> activityList;

    public static ActivityImportDTO deserialize(String surveyActivities) {
        GsonBuilder builder = SurveyActivity.getGsonBuilder();
        return builder.create().fromJson(surveyActivities, ActivityImportDTO.class);
    }

    public List<SurveyActivity> getActivityList() {
        return this.activityList;
    }
}
