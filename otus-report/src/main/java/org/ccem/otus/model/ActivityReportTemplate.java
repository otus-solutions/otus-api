package org.ccem.otus.model;

import java.util.ArrayList;

public class ActivityReportTemplate extends ReportTemplate{

    private String objectType;
    private String acronym;
    private ArrayList<Integer> versions;

    public ActivityReportTemplate(){
        super();
    }

    public static ActivityReportTemplate deserialize(String activityReportTemplateJson) {
        return ReportTemplate.getGsonBuilder().create().fromJson(activityReportTemplateJson, ActivityReportTemplate.class);
    }
}
