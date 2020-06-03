package org.ccem.otus.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

public class FieldCenter {
  private ObjectId _id;

  private String name;

  private Integer code;

  private String acronym;

  private String country;

  private String state;

  private String address;

  private String complement;

  private String zip;

  private String phone;

  private String backgroundColor;

  private String borderColor;

  private ObjectId locationPoint;

  public ObjectId getId() {
    return _id;
  }

  public Boolean isValid() {
    return !name.isEmpty() && !acronym.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
  }

  public String getAcronym() {
    return acronym;
  }

  public static String serialize(FieldCenter fieldCenter) {
    return getGsonBuilder().create().toJson(fieldCenter);
  }

  public static FieldCenter deserialize(String fieldCenterJson) {
    FieldCenter fieldCenter = FieldCenter.getGsonBuilder().create().fromJson(fieldCenterJson, FieldCenter.class);
    return fieldCenter;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    return builder;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getComplement() {
    return complement;
  }

  public void setComplement(String complement) {
    this.complement = complement;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public String getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }

  public void setLocationPoint(ObjectId locationPoint) {
    this.locationPoint = locationPoint;
  }

  public ObjectId getLocationPoint() {
    return locationPoint;
  }
}
