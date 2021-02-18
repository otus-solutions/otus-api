package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.LongAdapter;

import java.time.LocalDateTime;

public abstract class SerializableModel {

  protected static Object deserialize(String json, Class clazz){
    return new GsonBuilder().create().fromJson(json, clazz);
  }

  /* Non static methods */

  public String serialize(){
    return getGsonBuilderNonStatic().create().toJson(this);
  }

  public <T> T deserializeNonStatic(String json){
    return (T) getGsonBuilderNonStatic().create().fromJson(json, this.getClass());
  }

  protected GsonBuilder getGsonBuilderNonStatic() {
    GsonBuilder builder = new GsonBuilder();
    registerSpecificTypeAdapter(builder);
    return builder;
  }

  /* Override or not by child class */
  protected void registerSpecificTypeAdapter(GsonBuilder builder){ }

  protected void registerGsonBuilderLongAdapter(GsonBuilder builder){
    builder.registerTypeAdapter(Long.class, new LongAdapter());
  }

  protected void registerGsonBuilderLocalDateTimeAdapter(GsonBuilder builder){
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
  }

}
