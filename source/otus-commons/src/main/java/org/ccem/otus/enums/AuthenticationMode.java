package org.ccem.otus.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum AuthenticationMode {

  USER("user"),
  CLIENT("client"),
  PARTICIPANT("participant"),
  ACTIVITY_SHARING("sharing");

  private String name;

  AuthenticationMode(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static AuthenticationMode valueFromName(String otherMode){
    return Arrays.asList(AuthenticationMode.values()).stream()
      .filter(authenticationMode -> authenticationMode.getName().equals(otherMode))
      .collect(Collectors.toList())
      .get(0);
  }
}
