package org.ccem.otus.participant.model;

import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Participant {

  @Equalization(name = "recruitmentNumber")
  private Long recruitmentNumber;
  @Equalization(name = "name")
  private String name;
  @Equalization(name = "sex")
  private Sex sex;
  @Equalization(name = "birthdate")
  private ImmutableDate birthdate;
  @Equalization(name = "fieldCenter")
  private FieldCenter fieldCenter;
  private Boolean late;
  @Equalization(name = "email")
  private String email;
  @Equalization(name = "password")
  private String password;
  @Equalization(name = "tokenList")
  private ArrayList<String> tokenList;
  private String registeredBy;
  private Boolean identified;

  public Participant(Long recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public void setRecruitmentNumber(Long recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FieldCenter getFieldCenter() {
    return fieldCenter;
  }

  public Boolean isIdentified() {
    return identified;
  }

  public void setFieldCenter(FieldCenter fieldCenter) {
    this.fieldCenter = fieldCenter;
  }

  public Sex getSex() {
    return sex;
  }

  public void setSex(Sex sex) {
    this.sex = sex;
  }

  public ImmutableDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(ImmutableDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getPassword() {
    return password;
  }

  public String getRegisteredBy() {
    return registeredBy;
  }

  public void setRegisteredBy(String registeredBy) {
    this.registeredBy = registeredBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Participant that = (Participant) o;

    return recruitmentNumber != null ? recruitmentNumber.equals(that.recruitmentNumber) : that.recruitmentNumber == null;
  }

  @Override
  public int hashCode() {
    return recruitmentNumber != null ? recruitmentNumber.hashCode() : 0;
  }

  public Boolean getLate() {
    return late;
  }

  public void setLate(Boolean late) {
    this.late = late;
  }

  public static String serialize(Participant participantJson) {
    return Participant.getGsonBuilder().create().toJson(participantJson);
  }

  public static Participant deserialize(String participantJson) {
    Participant participant = Participant.getGsonBuilder().create().fromJson(participantJson, Participant.class);
    return participant;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder;
  }

  public void setTokenList(ArrayList<String> tokenList) {
    this.tokenList = tokenList;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
