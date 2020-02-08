package br.org.otus.laboratory.project.exam.examLot;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExamLot {

  private ObjectId _id;
  private String objectType;
  private String aliquotName;
  private String code;
  private List<Aliquot> aliquotList;
  private LocalDateTime realizationDate;
  private String operator;
  private FieldCenter fieldCenter;

  public ExamLot() {
    objectType = "ExamLot";
  }

  public static ExamLot deserialize(String examLot) {
    return getGsonBuilder().create().fromJson(examLot, ExamLot.class);
  }

  public static String serialize(ExamLot examLot) {
    Gson builder = getGsonBuilder().create();
    return builder.toJson(examLot);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    builder.serializeNulls();

    return builder;
  }

  public String getObjectType() {
    return objectType;
  }

  public List<Aliquot> getAliquotList() {
    return aliquotList;
  }

  public void setAliquotList(List<Aliquot> aliquotList) {
    this.aliquotList = aliquotList;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public FieldCenter getFieldCenter() {
    return fieldCenter;
  }

  public void setFieldCenter(FieldCenter fieldCenter) {
    this.fieldCenter = fieldCenter;
  }

  public LocalDateTime getRealizationDate() {
    return realizationDate;
  }

  public void setRealizationDate(LocalDateTime realizationDate) {
    this.realizationDate = realizationDate;
  }

  public String getAliquotName() {
    return aliquotName;
  }

  public void setAliquotName(String aliquotName) {
    this.aliquotName = aliquotName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    ExamLot that = (ExamLot) o;

    return code.equals(that.code);
  }

  @Override
  public int hashCode() {
    return code.hashCode();
  }

  public ArrayList<String> getAliquotCodeList() {
    ArrayList<String> codeList = new ArrayList<>();

    if (aliquotList != null) {
      aliquotList.forEach(aliquot -> {
        codeList.add(aliquot.getCode());
      });
    }
    return codeList;
  }

  public ArrayList<String> getNewAliquotCodeList(ArrayList<String> aliquotCodeList) {
    ArrayList<String> newAliquots = new ArrayList<>();
    aliquotCodeList.forEach(aliquotCode -> {
      if (!getAliquotCodeList().contains(aliquotCode)) {
        newAliquots.add(aliquotCode);
      }
    });
    return newAliquots;
  }

  public ArrayList<String> getRemovedAliquotCodeList(ArrayList<String> aliquotCodeList) {
    ArrayList<String> removedAliquots = new ArrayList<>();
    if (aliquotList != null) {
      if (aliquotCodeList.isEmpty()) {
        removedAliquots.addAll(getAliquotCodeList());
      } else {
        aliquotList.forEach(aliquot -> {
          if (!aliquotCodeList.contains(aliquot.getCode())) {
            removedAliquots.add(aliquot.getCode());
          }
        });
      }
    }
    return removedAliquots;
  }

  public ObjectId getLotId() {
    return _id;
  }
}
