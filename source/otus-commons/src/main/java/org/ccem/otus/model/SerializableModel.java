package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public abstract class SerializableModel {

  public static String serialize(Object stage) {
    return getGsonBuilder().create().toJson(stage);
  }

  public static Object deserialize(String json, Class clazz){
    return getGsonBuilder().create().fromJson(json, clazz);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

}
