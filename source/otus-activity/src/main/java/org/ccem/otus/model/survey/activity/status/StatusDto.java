package org.ccem.otus.model.survey.activity.status;

import org.ccem.otus.model.SerializableModel;
import org.ccem.otus.model.survey.activity.User;

public class StatusDto extends SerializableModel {

  private String name;
  private User user;
  private String date;


  public static StatusDto deserialize(String json){
    return (StatusDto)deserialize(json, StatusDto.class);
  }
}
