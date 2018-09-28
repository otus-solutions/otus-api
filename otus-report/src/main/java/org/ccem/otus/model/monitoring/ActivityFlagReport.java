package org.ccem.otus.model.monitoring;

public class ActivityFlagReport {

  private Integer rn;
  private String acronym;
  private Integer status;

  public ActivityFlagReport(String acronym) {
    this.acronym = acronym;
    this.status = null;
  }

  public void setRn(Integer rn) {
    this.rn = rn;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
