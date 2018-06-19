package org.ccem.otus.participant.model;


import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.GsonBuilder;


public class Monitoring {
 
  private String name;
  private Long goal;
  private FieldCenter center;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Long getGoal() {
    return goal;
  }
  public void setGoal(Long goal) {
    this.goal = goal;
  }
  public FieldCenter getCenter() {
    return center;
  }
  public void setCenter(FieldCenter center) {
    this.center = center;
  }
  
  public static String serialize(Monitoring monitoring) {
    return getGsonBuilder().create().toJson(monitoring);
}

public static Monitoring deserialize(String examResultLotJson) {
    return Monitoring.getGsonBuilder().create().fromJson(examResultLotJson, Monitoring.class);
}

private static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
//    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());

    return builder;
}
  

}
