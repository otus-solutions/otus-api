package org.ccem.otus.model.survey.activity.activityReview;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import org.ccem.otus.model.survey.activity.User;

import org.ccem.otus.utils.ObjectIdAdapter;

public class ActivityReview {
    private ObjectId activityId;
    private String reviewDate;
    private User userLogged;

    public static String serialize(ActivityReview activityReview) {
        return getGsonBuilder().create().toJson(activityReview);
    }

    public static ActivityReview deserialize(String activityReview) {
        return ActivityReview.getGsonBuilder().create().fromJson(activityReview, ActivityReview.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());

        return builder;
    }
}
