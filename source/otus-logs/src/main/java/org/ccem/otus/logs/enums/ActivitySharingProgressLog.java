package org.ccem.otus.logs.enums;

public enum ActivitySharingProgressLog {
  CREATE("create"),
  RENEW("renew"),
  ACCESS("access"),
  DELETION("deletion");

  private final String value;

  public String getValue() {
    return value;
  }

  private ActivitySharingProgressLog(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }
}