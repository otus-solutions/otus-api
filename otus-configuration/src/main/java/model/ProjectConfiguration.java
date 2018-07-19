package model;


import com.google.gson.GsonBuilder;

public class ProjectConfiguration {

    public static String DEFAULT_OBJECT_TYPE= "Project Configuration";

    public String objectType;
    private boolean participantRegistration;


    public static String serialize(ProjectConfiguration configuration) {
        return getGsonBuilder().create().toJson(configuration);
    }

    public static ProjectConfiguration deserialize(String configurationJson) {
        return ProjectConfiguration.getGsonBuilder().create().fromJson(configurationJson, ProjectConfiguration.class);
    }


    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
