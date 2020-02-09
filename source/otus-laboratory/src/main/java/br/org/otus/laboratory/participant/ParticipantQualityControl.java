package br.org.otus.laboratory.participant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParticipantQualityControl {

  private Long recruitmentNumber;
  private String code;

  public ParticipantQualityControl(Long recruitmentNumber, String code) {
    this.recruitmentNumber = recruitmentNumber;
    this.code = code;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getCode() {
    return code;
  }

  public static ParticipantQualityControl deserialize(String qualityControlJson) {
    Gson builder = ParticipantQualityControl.getGsonBuilder();
    return builder.fromJson(qualityControlJson, ParticipantQualityControl.class);
  }

  public static Gson getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.disableHtmlEscaping();

    return builder.create();
  }

}
