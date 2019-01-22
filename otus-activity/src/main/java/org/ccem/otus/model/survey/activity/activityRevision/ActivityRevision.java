package org.ccem.otus.model.survey.activity.activityRevision;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import org.ccem.otus.model.survey.activity.User;

import org.ccem.otus.utils.ObjectIdAdapter;

public class ActivityRevision {
    private ObjectId activityId;
    private String reviewDate;
    private User userReviewer;

    public static String serialize(ActivityRevision activityRevision) {
        return getGsonBuilder().create().toJson(activityRevision);
    }

    public static ActivityRevision deserialize(String activityReview) {
        return ActivityRevision.getGsonBuilder().create().fromJson(activityReview, ActivityRevision.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());

        return builder;
    }
}
