package org.ccem.otus.model.survey.activity.status;

public enum ActivityStatusOptions {
  CREATED("CREATED"),
  INITIALIZED_OFFLINE("INITIALIZED_OFFLINE"),
  INITIALIZED_ONLINE("INITIALIZED_ONLINE"),
  OPENED("OPENED"),
  SAVED("SAVED"),
  FINALIZED("FINALIZED"),
  REOPENED("REOPENED");

  private String name;

  public String getName() {
    return name;
  }

  ActivityStatusOptions(String name) {
    this.name = name;
  }
}





