package org.ccem.otus.model.survey.activity.configuration;

public class ActivityCategory {

    private String name;
    private String label;
    private Boolean deleted;
    private Boolean isDefault;

    public ActivityCategory(String name, String label, Boolean isDefault) {
        this.name = name;
        this.label = label;
        this.deleted = false;
        this.isDefault = isDefault;
    }
}
