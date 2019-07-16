package org.ccem.otus.model.survey.activity.variables;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

public class Variables {
    private long identification;
    private String name;
    private String value;
    private String sending;

    public long getIdentification() {
        return identification;
    }

    public void setIdentification(long identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSending() {
        return sending;
    }

    public void setSending(String sending) {
        this.sending = sending;
    }

     public static String serialize(Variables variables) {
        return getGsonBuilder().create().toJson(variables);
    }

    public static Variables deserialize(String variables) {
        return Variables.getGsonBuilder().create().fromJson(variables, Variables.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());

        return builder;
    }
}
