package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

public abstract class SerializableModel {

  public String serialize(){
    return getGsonBuilderNonStatic().create().toJson(this);
  }

  protected static Object deserialize(String json, Class clazz){
    return new GsonBuilder().create().fromJson(json, clazz);
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
