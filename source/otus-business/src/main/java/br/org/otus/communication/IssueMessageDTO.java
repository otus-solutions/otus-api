package br.org.otus.communication;

import com.google.gson.GsonBuilder;

public class IssueMessageDTO {
  private String objectType;
  private String sender;
  private String title;
  private String message;
  private String creationDate;
  private String status;

  public void setSender(String emailReporter) {
    this.sender = emailReporter;
  }

  public String getSender() {
    return sender;
  }

  public static String serialize(IssueMessageDTO issueMessageDTO) {
    return IssueMessageDTO.getGsonBuilder().create().toJson(issueMessageDTO);
  }

  public static IssueMessageDTO deserialize(String issueMessageDTO) {
    return IssueMessageDTO.getGsonBuilder().create().fromJson(issueMessageDTO, IssueMessageDTO.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }
}
