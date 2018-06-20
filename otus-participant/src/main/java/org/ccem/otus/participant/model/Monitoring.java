package org.ccem.otus.participant.model;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.GsonBuilder;

public class Monitoring {

  private String name;
  private Long goal;
  private String backgroundColor;
  private String borderColor;

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

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public String getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }

}
