package br.org.otus.communication;

import com.google.gson.GsonBuilder;

public class IssueMessageDTO {
  private String objectType;
  private String emailReporter;
  private String title;
  private String message;
  private String creationDate;
  private String status;

  public void setEmailReporter(String emailReporter) {
    this.emailReporter = emailReporter;
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
