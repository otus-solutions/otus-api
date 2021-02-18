package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

public abstract class SerializableModel {

  public String serialize(){
    return getGsonBuilderNonStatic().create().toJson(this);
  }

  protected static Object deserialize(String json, Class clazz){
    return getGsonBuilder().create().fromJson(json, clazz);
  }

  protected static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

  /* Non static methods */

  protected GsonBuilder getGsonBuilderNonStatic() {
    GsonBuilder builder = new GsonBuilder();
    registerSpecificTypeAdapter(builder);
    return builder;
  }

  /* Override or not by child class */
  protected void registerSpecificTypeAdapter(GsonBuilder builder){ }

}
