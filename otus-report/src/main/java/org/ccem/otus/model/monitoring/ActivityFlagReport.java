package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;

public class ActivityFlagReport {

  private Integer rn;
  private String acronym;
  private Integer status;

  public ActivityFlagReport(String acronym) {
    this.acronym = acronym;
    this.status = null;
  }

  public ActivityFlagReport(Integer rn, String acronym) {
    this.rn = rn;
    this.acronym = acronym;
  }

  public String getAcronym() {
    return acronym;
  }

  public void setRn(Integer rn) {
    this.rn = rn;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
