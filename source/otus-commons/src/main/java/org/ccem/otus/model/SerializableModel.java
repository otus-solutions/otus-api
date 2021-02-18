package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

public abstract class SerializableModel {

  public String toJson(){
    return getGsonBuilder().create().toJson(this);
  }

  protected static Object deserialize(String json, Class clazz){
    return getGsonBuilder().create().fromJson(json, clazz);
  }

  protected static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

}
