package br.org.otus.communication;

import com.google.gson.GsonBuilder;

public class MessageDTO {
  private String text;
  private String sender;
  private String issueId;

  public String getTexte() {
    return text;
  }

  public void setTexte(String texte) {
    this.text = texte;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getIssueId() {
    return issueId;
  }

  public void setIssueId(String issueId) {
    this.issueId = issueId;
  }

  public static String serialize(MessageDTO messageDTO) {
    return MessageDTO.getGsonBuilder().create().toJson(messageDTO);
  }

  public static MessageDTO deserialize(String messageDTO) {
    return MessageDTO.getGsonBuilder().create().fromJson(messageDTO, MessageDTO.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }
}
