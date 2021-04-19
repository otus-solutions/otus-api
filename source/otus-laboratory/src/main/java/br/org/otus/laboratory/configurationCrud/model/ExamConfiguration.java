package br.org.otus.laboratory.configurationCrud.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class ExamConfiguration {
    ObjectId _id;
    String objectType;
    String name;
    String label;

    public ExamConfiguration(String name, String label) {
        this.objectType = "examConfiguration";
        this.name = name;
        this.label = label;
    };

    public static String serialize(ExamConfiguration examConfiguration) {
        GsonBuilder builder = ExamConfiguration.getGsonBuilder();
        return builder.create().toJson(examConfiguration);
    }

    public static ExamConfiguration deserialize(String examConfigurationJson) {
        GsonBuilder builder = ExamConfiguration.getGsonBuilder();
        builder.registerTypeAdapter(Long.class, new LongAdapter());
        return builder.create().fromJson(examConfigurationJson, ExamConfiguration.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        return builder;
    }
}
