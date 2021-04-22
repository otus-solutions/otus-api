package br.org.otus.laboratory.configurationCrud.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class AliquotConfiguration {
    ObjectId _id;
    String objectType;
    String name;
    String label;
    String type;

    public AliquotConfiguration(String name, String label, String type) {
        this.objectType = "aliquotConfiguration";
        this.name = name;
        this.label = label;
        this.type = type;
    };

    public static String serialize(AliquotConfiguration aliquotConfiguration) {
        GsonBuilder builder = AliquotConfiguration.getGsonBuilder();
        return builder.create().toJson(aliquotConfiguration);
    }

    public static AliquotConfiguration deserialize(String aliquotConfigurationJson) {
        GsonBuilder builder = AliquotConfiguration.getGsonBuilder();
        return builder.create().fromJson(aliquotConfigurationJson, AliquotConfiguration.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        builder.registerTypeAdapter(Long.class, new LongAdapter());
        return builder;
    }
}
