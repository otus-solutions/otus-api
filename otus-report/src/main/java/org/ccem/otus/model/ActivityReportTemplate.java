package org.ccem.otus.model;

public class ActivityReportTemplate extends ReportTemplate{

    private String objectType;
    private String acronym;
    private Integer version;

    public ActivityReportTemplate(){
        super();
    }

    public static ActivityReportTemplate deserialize(String activityReportTemplateJson) {
        return ReportTemplate.getGsonBuilder().create().fromJson(activityReportTemplateJson, ActivityReportTemplate.class);
    }
}
