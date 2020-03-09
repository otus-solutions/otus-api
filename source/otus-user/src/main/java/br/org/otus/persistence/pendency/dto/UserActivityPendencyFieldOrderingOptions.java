package br.org.otus.persistence.pendency.dto;

import java.util.Arrays;

public enum UserActivityPendencyFieldOrderingOptions {

  STATUS("status"),
  ACRONYM("acronym"),
  RECRUITMENT_NUMBER("rn"),
  EXTERNAL_ID("externalID"),
  DUE_DATE("dueDate"),
  REQUESTER("requester"),
  RECEIVER("receiver");

  private String name;

  UserActivityPendencyFieldOrderingOptions(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static boolean contains(String orderingOption){
    return Arrays.asList(UserActivityPendencyFieldOrderingOptions.values()).stream()
      .map(UserActivityPendencyFieldOrderingOptions::getName)
      .anyMatch(orderingOption::equals);
  }

}
