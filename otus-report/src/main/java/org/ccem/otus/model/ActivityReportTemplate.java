package org.ccem.otus.model;

public class ActivityReportTemplate extends ReportTemplate{

    private String objectType;
    private String acronym;
    private Integer version;

    public ActivityReportTemplate(){
        super();
    }

    public static ActivityReportTemplate deserialize(String reportTemplateJson) {
        return ReportTemplate.getGsonBuilder().create().fromJson(reportTemplateJson, ActivityReportTemplate.class);
    }
}
