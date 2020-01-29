package br.org.otus.laboratory.project.transportation.persistence;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class TransportationAliquotFiltersDTO {

  private String code;
  private String initialDate;
  private String finalDate;
  private String fieldCenter;
  private String role;
  private ArrayList aliquotList;
  private ObjectId transportationLotId;

  public String getCode() {
    return code;
  }

  public String getInitialDate() {
    return initialDate;
  }

  public String getFinalDate() {
    return finalDate;
  }

  public String getFieldCenter() {
    return fieldCenter;
  }

  public String getRole() {
    return role;
  }

  public ArrayList getAliquotList() {
    return aliquotList;
  }

  public ObjectId getTransportationLotId() {
    return transportationLotId;
  }


  public static String serialize(TransportationAliquotFiltersDTO participantDataSourceResult) {
    return getGsonBuilder().create().toJson(participantDataSourceResult);
  }

  public static TransportationAliquotFiltersDTO deserialize(String DataSource) {
    GsonBuilder builder = TransportationAliquotFiltersDTO.getGsonBuilder();
    return builder.create().fromJson(DataSource, TransportationAliquotFiltersDTO.class);
  }

  private static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }
}
