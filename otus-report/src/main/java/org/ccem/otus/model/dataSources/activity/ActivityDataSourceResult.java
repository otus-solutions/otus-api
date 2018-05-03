package org.ccem.otus.model.dataSources.activity;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.List;

public class ActivityDataSourceResult {
    private List<ActivityStatus> statusHistory;

    public static String serialize(ActivityDataSourceResult activityDataSourceResult) {
        return getGsonBuilder().create().toJson(activityDataSourceResult);
    }

    public static ActivityDataSourceResult deserialize(String DataSource) {
        GsonBuilder builder = ActivityDataSourceResult.getGsonBuilder();
        return builder.create().fromJson(DataSource, ActivityDataSourceResult.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return builder;
    }

    public List<ActivityStatus> getStatusHistory() {
        return statusHistory;
    }
}
