package org.ccem.otus.model.survey.activity.permission;

import java.util.List;

public class Permission {
  
  private String objectType;  
  private Integer version;
  private String acronym;
  private List<String> exclusiveDisjunction;  
  
  public Integer getVersion() {
    return version;
  }
  public String getAcronym() {
    return acronym;
  }
  public List<String> getExclusiveDisjunction() {
    return exclusiveDisjunction;
  }

}
