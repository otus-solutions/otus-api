package br.org.otus.laboratory.configurationCrud.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class TubeConfiguration {
    String objectType;
    ObjectId _id;
    String name;
    String label;
    String color;

    public TubeConfiguration(String name, String label, String color) {
        this.objectType = "tubeConfiguration";
        this.name = name;
        this.label = label;
        this.color = color;
    };

    public static String serialize(TubeConfiguration tubeConfiguration) {
        GsonBuilder builder = TubeConfiguration.getGsonBuilder();
        return builder.create().toJson(tubeConfiguration);
    }

    public static TubeConfiguration deserialize(String tubeConfigurationJson) {
        GsonBuilder builder = TubeConfiguration.getGsonBuilder();
        builder.registerTypeAdapter(Long.class, new LongAdapter());
        return builder.create().fromJson(tubeConfigurationJson, TubeConfiguration.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        return builder;
    }
}
