package br.org.otus.laboratory.configurationCrud.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class ControlGroupConfiguration {
    ObjectId _id;
    String objectType;
    String name;
    String type;

    public ControlGroupConfiguration(String name, String type) {
        this.objectType = "controlGroupConfiguration";
        this.name = name;
        this.type = type;
    };

    public static String serialize(ControlGroupConfiguration controlGroup) {
        GsonBuilder builder = ControlGroupConfiguration.getGsonBuilder();
        return builder.create().toJson(controlGroup);
    }

    public static ControlGroupConfiguration deserialize(String controlGroupJson) {
        GsonBuilder builder = ControlGroupConfiguration.getGsonBuilder();
        return builder.create().fromJson(controlGroupJson, ControlGroupConfiguration.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        builder.registerTypeAdapter(Long.class, new LongAdapter());
        return builder;
    }
}
