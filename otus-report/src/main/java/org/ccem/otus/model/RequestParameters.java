package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class RequestParameters {
    private long recruitmentNumber;
    private ObjectId reportId;

    public static RequestParameters deserialize(String requestParameters) {
        GsonBuilder builder = RequestParameters.getGsonBuilder();
        return builder.create().fromJson(requestParameters, RequestParameters.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        return builder;
    }

    public ObjectId getReportId() {
        return reportId;
    }

    public long getRecruitmentNumber() {
        return recruitmentNumber;
    }
}
