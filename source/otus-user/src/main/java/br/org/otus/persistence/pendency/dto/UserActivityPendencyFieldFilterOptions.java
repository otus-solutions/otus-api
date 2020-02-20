package br.org.otus.persistence.pendency.dto;

import java.util.Arrays;

public enum UserActivityPendencyFieldFilterOptions {

  STATUS("status"),
  ACRONYM("acronym"),
  RECRUITMENT_NUMBER("rn"),
  EXTERNAL_ID("externalID"),
  DUE_DATE("dueDate"),
  REQUESTER("requester"),
  RECEIVER("receiver");

  private String name;

  UserActivityPendencyFieldFilterOptions(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static boolean contains(String filterOption){
    for (UserActivityPendencyFieldFilterOptions option : UserActivityPendencyFieldFilterOptions.values()) {
      if (option.getName().equals(filterOption)) {
        return true;
      }
    }
    return false;
  }
}
