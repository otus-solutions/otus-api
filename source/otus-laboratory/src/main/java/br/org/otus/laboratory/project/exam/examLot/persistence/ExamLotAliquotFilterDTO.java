package br.org.otus.laboratory.project.exam.examLot.persistence;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.FieldCenter;

public class ExamLotAliquotFilterDTO {
  private String aliquotCode;
  private FieldCenter fieldCenter;
  private String lotType;

  public String getLotType() {
    return lotType;
  }

  public FieldCenter getFieldCenter() {
    return fieldCenter;
  }

  public String getAliquotCode() {
    return aliquotCode;
  }

  public static ExamLotAliquotFilterDTO deserialize(String examLotAliquotFilterJson) {
    GsonBuilder builder = ExamLotAliquotFilterDTO.getGsonBuilder();
    return builder.create().fromJson(examLotAliquotFilterJson, ExamLotAliquotFilterDTO.class);
  }

  private static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }
}
