package br.org.otus.laboratory.project.transportation.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.util.ArrayList;

public class TransportLocationPointListDTO {
  private class TransportLocationPointDTO {
    ObjectId _id;
    String name;
    ArrayList<String> users;
  }

  ArrayList<TransportLocationPointDTO> transportLocationPoints;

  public static JsonElement serializeToJsonTree(TransportLocationPointListDTO transportLocationPointListDTO) {
    Gson builder = getGsonBuilder().create();
    return builder.toJsonTree(transportLocationPointListDTO);
  }

  public static TransportLocationPointListDTO deserialize(String transportationLot) {
    return getGsonBuilder().create().fromJson(transportationLot, TransportLocationPointListDTO.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.serializeNulls();
    return builder;
  }
}
