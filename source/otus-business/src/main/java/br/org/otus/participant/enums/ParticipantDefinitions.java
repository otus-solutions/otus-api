package br.org.otus.participant.enums;

public enum ParticipantDefinitions {
  TEMPLATE_RESET_PASSWD_PARTICIPANT_ID("5ea88862ae51d800083aeba7");

  private String value;

  ParticipantDefinitions(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
