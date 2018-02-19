package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

public class RequestParameters {
    private Long recruitmentNumber;
    private Long reportId;

    public static RequestParameters deserialize(String requestParameters) {
        GsonBuilder builder = RequestParameters.getGsonBuilder();
        return builder.create().fromJson(requestParameters, RequestParameters.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }

    public Long getReportId() {
        return reportId;
    }

    public Long getRecruitmentNumber() {
        return recruitmentNumber;
    }
}
