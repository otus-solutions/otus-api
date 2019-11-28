package org.ccem.otus.participant.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LongAdapter implements JsonDeserializer<Long> {

  private static String NUMBER_LONG = "$numberLong";

  @Override
  public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    if (json.isJsonObject()) {
      return json.getAsJsonObject().get(NUMBER_LONG).getAsLong();
    }
    return json.getAsLong();
  }

}
