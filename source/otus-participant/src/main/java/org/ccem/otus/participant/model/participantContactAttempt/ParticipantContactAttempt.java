package org.ccem.otus.participant.model.participantContactAttempt;

import com.google.gson.GsonBuilder;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.Address;
import org.ccem.otus.participant.model.participant_contact.AttemptStatus;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.LocalDateTime;
import java.util.Arrays;


public class ParticipantContactAttempt {

  private ObjectId _id;
  private String objectType;
  private Long recruitmentNumber;
  private Address address;
  private LocalDateTime attemptDateTime;
  private AttemptStatus attemptStatus;
  private ObjectId registeredBy;
  private String userEmail;
  private Boolean isValid;

  public ParticipantContactAttempt(
    String objectType,
    Long recruitmentNumber,
    Address address,
    LocalDateTime attemptDateTime,
    AttemptStatus attemptStatus,
    ObjectId registeredBy,
    String userEmail,
    Boolean isValid
  ) {
    this.objectType = objectType;
    this.recruitmentNumber = recruitmentNumber;
    this.address = address;
    this.attemptDateTime = attemptDateTime;
    this.attemptStatus = attemptStatus;
    this.registeredBy = registeredBy;
    this.userEmail = userEmail;
    this.isValid = isValid;
  }

  public ObjectId get_id() {
    return _id;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public Object getAddress() {
    return address;
  }

  public String getUserEmail() { return userEmail; }
  public String getAttemptStatus() { return attemptStatus.getValue(); }
  public LocalDateTime getAttemptDateTime() { return attemptDateTime; }
  public String getAttemptAddressStreet() { return address.getStreet(); }
  public Integer getAttemptAddressNumber() { return address.getStreetNumber(); }
  public String getAttemptAddressZipCode() { return address.getPostalCode(); }
  public String getAttemptAddressComplement() { return address.getComplements(); }
  public String getAttemptAddressDistrict() { return address.getNeighbourhood(); }
  public String getAttemptAddressCity() { return address.getCity(); }
  public String getAttemptAddressState() { return address.getState(); }
  public String getAttemptAddressCountry() { return address.getCountry(); }
  public String getAttemptSectorIBGE() { return address.getCensus(); }

  public void setRegisteredBy(ObjectId registeredBy) {
    this.registeredBy = registeredBy;
  }

  public static String serialize(ParticipantContactAttempt participantContactJson) {
    GsonBuilder builder = ParticipantContactAttempt.getGsonBuilder();
    return builder.create().toJson(participantContactJson);
  }

  public static ParticipantContactAttempt deserialize(String participantJson) {
    GsonBuilder builder = ParticipantContactAttempt.getGsonBuilder();
    return builder.create().fromJson(participantJson, ParticipantContactAttempt.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder;
  }

  public void setValid(Boolean valid) {
    isValid = valid;
  }
}
