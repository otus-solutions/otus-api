package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

public abstract class SerializableModel {

  protected static String serialize(Object object) {
    return getGsonBuilder().create().toJson(object);
  }

  protected static Object deserialize(String json, Class clazz){
    return getGsonBuilder().create().fromJson(json, clazz);
  }

  protected static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

}
