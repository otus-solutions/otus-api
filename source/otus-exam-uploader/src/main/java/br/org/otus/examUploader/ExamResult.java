package br.org.otus.examUploader;

import br.org.otus.examUploader.utils.ObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.util.List;

public class ExamResult {

  private ObjectId examSendingLotId;
  private ObjectId examId;
  private ObjectId _id;

  private String objectType;
  private String code;
  private String examName;
  private String resultName;
  private String value;
  private Boolean isValid;
  private String releaseDate;
  private String realizationDate;
  private String cutOffValue;
  private List<Observation> observations;

  private List<ExtraVariable> extraVariables;

  private FieldCenter fieldCenter;
  private Long recruitmentNumber;
  private Sex sex;
  private ImmutableDate birthdate;

  public String getRealizationDate() { return this.realizationDate; }
  public void setRealizationDate(String realizationDate) { this.realizationDate = realizationDate;

  public List<ExtraVariable> getExtraVariables() {
    return this.extraVariables;
  }

  public void setExtraVariables(List<ExtraVariable> extraVariables) {
    this.extraVariables = extraVariables;
  }

  public void setExamId(ObjectId examId) {
    this.examId = examId;
  }

  public void setExamSendingLotId(ObjectId examId) {
    this.examSendingLotId = examId;
  }

  public void setCutOffValue(String cutOffValue) {
    this.cutOffValue = cutOffValue;
  }

  public String getCutOffValue() {
    return this.cutOffValue;
  }

  public void setFieldCenter(FieldCenter fieldCenter) {
    this.fieldCenter = fieldCenter;
  }

  public String getCode() {
    return code;
  }

  //used for unit testing
  public void setCode(String code) {
    this.code = code;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public ObjectId getExamId() {
    return examId;
  }

  public void setRecruitmentNumber(Long recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public void setSex(Sex sex) {
    this.sex = sex;
  }

  public void setBirthdate(ImmutableDate birthdate) {
    this.birthdate = birthdate;
  }

  public void setIsValid(boolean isValid) {
    this.isValid = isValid;
  }

  public static String serialize(ExamResult examResult) {
    return getGsonBuilder().create().toJson(examResult);
  }

  public static ExamResult deserialize(String examResultJson) {
    return ExamResult.getGsonBuilder().create().fromJson(examResultJson, ExamResult.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    return builder;
  }

  public String getExamName() {
    return examName;
  }

  public void setExamName(String examName) {
    this.examName = examName;
  }
}
