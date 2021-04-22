package br.org.otus.laboratory.configurationCrud.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class MomentConfiguration {
    ObjectId _id;
    String objectType;
    String name;
    String label;

    public MomentConfiguration(String name, String label) {
        this.objectType = "momentConfiguration";
        this.name = name;
        this.label = label;
    };

    public static String serialize(MomentConfiguration momentConfiguration) {
        GsonBuilder builder = MomentConfiguration.getGsonBuilder();
        return builder.create().toJson(momentConfiguration);
    }

    public static MomentConfiguration deserialize(String momentConfigurationJson) {
        GsonBuilder builder = MomentConfiguration.getGsonBuilder();
        return builder.create().fromJson(momentConfigurationJson, MomentConfiguration.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        builder.registerTypeAdapter(Long.class, new LongAdapter());
        return builder;
    }
}
