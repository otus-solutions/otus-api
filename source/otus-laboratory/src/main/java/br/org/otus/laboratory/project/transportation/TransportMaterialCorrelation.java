package br.org.otus.laboratory.project.transportation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class TransportMaterialCorrelation {


  private ObjectId _id;
  private ArrayList<String> aliquotCodeList;

  public TransportMaterialCorrelation(ObjectId transportationLotId, ArrayList<String> aliquotCodeList) {
    this._id = transportationLotId;
    this.aliquotCodeList = aliquotCodeList;
  }

  public static String serializeToJsonString(TransportMaterialCorrelation transportMaterialCorrelation) {
    Gson builder = getGsonBuilder().create();
    return builder.toJson(transportMaterialCorrelation);
  }


  public static TransportMaterialCorrelation deserialize(String transportMaterialCorrelation) {
    return getGsonBuilder().create().fromJson(transportMaterialCorrelation, TransportMaterialCorrelation.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.serializeNulls();
    return builder;
  }

  public ArrayList<String> getRemoved(ArrayList<String> newAliquotCodeList) {
    ArrayList<String> removedList = new ArrayList<>();
    this.aliquotCodeList.forEach(aliquotCode -> {
      boolean removed = !newAliquotCodeList.contains(aliquotCode);
      if (removed) {
        removedList.add(aliquotCode);
      }
    });
    return removedList;
  }
}
