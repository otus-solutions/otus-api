package br.org.otus.laboratory.participant.aliquot;

public class AliquotEvent {
  private String type;
  private String userEmail;
  private String description;
  private String date;

  public String getType() {
    return type;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public String getDescription() {
    return description;
  }

  public String getDate() {
    return date;
  }
}
