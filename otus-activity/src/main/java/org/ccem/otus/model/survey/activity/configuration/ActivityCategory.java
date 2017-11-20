package org.ccem.otus.model.survey.activity.configuration;

import com.sun.media.jfxmediaimpl.NativeMediaManager;

public class ActivityCategory {

    public static String NAMING_PREFIX = "C";
    public static String OBJECT_TYPE = "ActivityCategory"; //if you change this, script mongo to update the collection. The findNonDeleted method use this field

    private String name;
    private String objectType;
    private String label;
    private Boolean deleted;
    private Boolean isDefault;

    public ActivityCategory(String label) {
        this.objectType = OBJECT_TYPE;
        this.label = label;
        this.deleted = false;
        this.isDefault = false;
    }

    public ActivityCategory(String name, String label, Boolean deleted, Boolean isDefault) {
        this.objectType = OBJECT_TYPE;
        this.name = name;
        this.label = label;
        this.deleted = deleted;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public void setName(String lastInsertedName) {
        this.name = ActivityConfiguration.extractNamingSuffix(NAMING_PREFIX, lastInsertedName);
    }

    public void setName() {
        this.name = NAMING_PREFIX + "0";
    }

    public static String serialize(ActivityCategory activityCategory) {
        return ActivityConfiguration.getGsonBuilder().create().toJson(activityCategory);
    }

    public static ActivityCategory deserialize(String activityCategory) {
        return ActivityConfiguration.getGsonBuilder().create().fromJson(activityCategory, ActivityCategory.class);
    }
}
