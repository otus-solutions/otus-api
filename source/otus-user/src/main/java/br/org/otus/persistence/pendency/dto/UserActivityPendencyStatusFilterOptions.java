package br.org.otus.persistence.pendency.dto;

import java.util.HashMap;
import java.util.Map;

public enum UserActivityPendencyStatusFilterOptions {

  FINALIZED("FINALIZED"),
  NOT_FINALIZED("NOT_FINALIZED");

  private final String value;

  UserActivityPendencyStatusFilterOptions(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static boolean contains(String statusOption){
    Map<String, UserActivityPendencyStatusFilterOptions> valuesMap = new HashMap<>();
    valuesMap.put(FINALIZED.getValue(), FINALIZED);
    valuesMap.put(NOT_FINALIZED.getValue(), NOT_FINALIZED);
    return (valuesMap.get(statusOption) != null);
  }
}
