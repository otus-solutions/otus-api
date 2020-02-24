package br.org.otus.laboratory.project.transportation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

public class TransportLocationPoint {
  private ObjectId _id;
  private String name;

  public TransportLocationPoint(String name) {
    this._id = new ObjectId();
    this.name = name;
  }

  public ObjectId get_id() {
    return _id;
  }

  public String getName() {
    return name;
  }

  public static String serializeToJsonString(TransportLocationPoint transportLocationPoint) {
    Gson builder = getGsonBuilder().create();
    return builder.toJson(transportLocationPoint);
  }

  public static JsonElement serializeToJsonTree(TransportLocationPoint transportLocationPoint) {
    Gson builder = getGsonBuilder().create();
    return builder.toJsonTree(transportLocationPoint);
  }

  public static TransportLocationPoint deserialize(String transportationLot) {
    return getGsonBuilder().create().fromJson(transportationLot, TransportLocationPoint.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.serializeNulls();
    return builder;
  }
}
