package org.ccem.otus.permissions.model.user;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class SurveyGroupPermission extends Permission {
    private ArrayList<String> groups;

    public static String serialize(Permission permission) {
        return SurveyGroupPermission.getGsonBuilder().create().toJson(permission);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
