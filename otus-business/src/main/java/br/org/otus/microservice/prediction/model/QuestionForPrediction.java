package br.org.otus.microservice.prediction.model;

import com.google.gson.GsonBuilder;

public class QuestionForPrediction {

  private String id;
  private String customId;
  private String type;
  private String answer;

  public String getCustomId() {
    return customId;
  }

  public void setCustomId(String customId) {
    this.customId = customId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
