package org.ccem.otus.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

public class LongAdapter implements JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if(json.isJsonObject()) {
            return json.getAsJsonObject().get("$numberLong").getAsLong();
        }
        return json.getAsLong();
    }

}
