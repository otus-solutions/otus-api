package org.ccem.otus.model.survey.activity.status;

import org.ccem.otus.model.SerializableModelWithID;
import org.ccem.otus.model.survey.activity.User;

public class StatusDto extends SerializableModelWithID {

  private String name;
  private User user;
  private String date;


  public StatusDto() {
  }

  public StatusDto(ActivityStatus activityStatus) {
    name = activityStatus.getName();
    user = activityStatus.getUser();
    date = activityStatus.getDate().toString();
  }
}
