package br.org.otus.persistence.pendency.dto;

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
}
