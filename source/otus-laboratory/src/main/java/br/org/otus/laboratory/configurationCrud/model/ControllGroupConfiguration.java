package br.org.otus.laboratory.configurationCrud.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class ControllGroupConfiguration {
    ObjectId _id;
    String objectType;
    String name;
    String type;

    public ControllGroupConfiguration(String name, String type) {
        this.objectType = "controllGroupConfiguration";
        this.name = name;
        this.type = type;
    };

    public static String serialize(ControllGroupConfiguration controllGroup) {
        GsonBuilder builder = ControllGroupConfiguration.getGsonBuilder();
        return builder.create().toJson(controllGroup);
    }

    public static ControllGroupConfiguration deserialize(String controllGroupJson) {
        GsonBuilder builder = ControllGroupConfiguration.getGsonBuilder();
        builder.registerTypeAdapter(Long.class, new LongAdapter());
        return builder.create().fromJson(controllGroupJson, ControllGroupConfiguration.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        return builder;
    }
}
