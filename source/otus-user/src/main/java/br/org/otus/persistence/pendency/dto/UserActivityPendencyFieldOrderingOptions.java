package br.org.otus.persistence.pendency.dto;

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
    for (UserActivityPendencyFieldOrderingOptions option : UserActivityPendencyFieldOrderingOptions.values()) {
      if (option.getName().equals(orderingOption)) {
        return true;
      }
    }
    return false;
  }

}
