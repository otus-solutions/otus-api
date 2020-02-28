package br.org.otus.persistence.pendency.dto;

public enum UserActivityPendencyStatusFilterOptions {

  ALL("ALL"),
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
    for (UserActivityPendencyStatusFilterOptions option: UserActivityPendencyStatusFilterOptions.values()) {
      if(option.getValue().equals(statusOption)){
        return true;
      }
    }
    return false;
  }
}
