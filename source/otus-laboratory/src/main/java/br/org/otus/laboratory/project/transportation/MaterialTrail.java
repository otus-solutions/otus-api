package br.org.otus.laboratory.project.transportation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;

public class MaterialTrail {
  private ObjectId _id;
  private String materialCode;
  private ObjectId operator;
  private LocalDateTime operationDate;
  private ObjectId locationPoint;
  private Boolean isCurrentLocation;
  private ObjectId transportationLotId;
  private Boolean isReceived;

  public MaterialTrail(ObjectId operator, String materialCode, TransportationLot transportationLot) {
    this.isCurrentLocation = true;
    this.operationDate = transportationLot.getShipmentDate();
    this.operator = operator;
    this.materialCode = materialCode;
    this.locationPoint = transportationLot.getDestinationLocationPoint();
    this.transportationLotId = transportationLot.getLotId();
    this.isReceived = false;
  }

  public ObjectId get_id() {
    return _id;
  }

  public String getMaterialCode() {
    return materialCode;
  }

  public ObjectId getOperator() {
    return operator;
  }

  public LocalDateTime getOperationDate() {
    return operationDate;
  }

  public ObjectId getLocationPoint() {
    return locationPoint;
  }

  public Boolean getCurrentLocation() {
    return isCurrentLocation;
  }

  public ObjectId getTransportationLotId() {
    return transportationLotId;
  }

  public static String serializeToJsonString(MaterialTrail materialTrail) {
    Gson builder = getGsonBuilder().create();
    return builder.toJson(materialTrail);
  }

  public static JsonElement serializeToJsonTree(MaterialTrail materialTrail) {
    Gson builder = getGsonBuilder().create();
    return builder.toJsonTree(materialTrail);
  }

  public static MaterialTrail deserialize(String materialTrailJson) {
    return getGsonBuilder().create().fromJson(materialTrailJson, MaterialTrail.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.serializeNulls();
    return builder;
  }

  public Boolean getReceived() {
    return isReceived;
  }

  public void setReceived(Boolean received) {
    isReceived = received;
  }
}