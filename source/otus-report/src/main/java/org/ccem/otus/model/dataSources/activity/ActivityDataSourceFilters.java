package org.ccem.otus.model.dataSources.activity;

import java.util.ArrayList;

public class ActivityDataSourceFilters {

  private String acronym = null;
  private String category = null;
  private String question = null;
  private ActivityDataSourceStatusHistoryFilter statusHistory = null;

  public String getAcronym() {
    return acronym;
  }

  public String getCategory() {
    return category;
  }

  public String getQuestion() {
    return question;
  }

  public ActivityDataSourceStatusHistoryFilter getStatusHistory() {
    return statusHistory;
  }
}
