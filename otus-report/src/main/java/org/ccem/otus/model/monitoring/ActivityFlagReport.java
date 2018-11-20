package org.ccem.otus.model.monitoring;

public class ActivityFlagReport {

  private Long rn;
  private String acronym;
  private Integer status;

  public ActivityFlagReport(String acronym) {
    this.acronym = acronym;
    this.status = null;
  }

  public ActivityFlagReport(Long rn, String acronym) {
    this.rn = rn;
    this.acronym = acronym;
  }

  public String getAcronym() {
    return acronym;
  }

  public void setRn(Long rn) {
    this.rn = rn;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
