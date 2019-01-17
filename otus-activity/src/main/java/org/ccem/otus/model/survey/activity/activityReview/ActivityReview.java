package org.ccem.otus.model.survey.activity.activityReview;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.utils.AnswerAdapter;
import org.ccem.otus.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class ActivityReview {

    public static String serialize(ActivityReview activityReview) {
        return getGsonBuilder().create().toJson(activityReview);
    }

    public static ActivityReview deserialize(String activityReview) {
//        GsonBuilder builder = getGsonBuilder();
//        builder.registerTypeAdapter(Long.class, new LongAdapter());
        return ActivityReview.getGsonBuilder().create().fromJson(activityReview, ActivityReview.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        return builder;

    }
}
