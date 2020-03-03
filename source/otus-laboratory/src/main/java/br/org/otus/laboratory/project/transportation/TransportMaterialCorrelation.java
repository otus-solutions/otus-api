package br.org.otus.laboratory.project.transportation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.util.ArrayList;

public class TransportMaterialCorrelation {


  private ObjectId _id;
  private ArrayList<String> aliquotCodeList;
  private ArrayList<String> tubeCodeList;

  public TransportMaterialCorrelation(ObjectId transportationLotId, ArrayList<String> aliquotCodeList, ArrayList<String> tubeCodeList) {
    this._id = transportationLotId;
    this.aliquotCodeList = aliquotCodeList;
    this.tubeCodeList = tubeCodeList;
  }

  public ArrayList<String> getAliquotCodeList() {
    return aliquotCodeList;
  }

  public ArrayList<String> getTubeCodeList() {
    return tubeCodeList;
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

  public ArrayList<String> getRemovedAliquots(ArrayList<String> newAliquotCodeList) {
    ArrayList<String> removedList = new ArrayList<>();
    this.aliquotCodeList.forEach(aliquotCode -> {
      boolean removed = !newAliquotCodeList.contains(aliquotCode);
      if (removed) {
        removedList.add(aliquotCode);
      }
    });
    return removedList;
  }

  public ArrayList<String> getRemovedTubes(ArrayList<String> newTubeCodeList) {
    ArrayList<String> removedList = new ArrayList<>();
    this.tubeCodeList.forEach(tubeCode -> {
      boolean removed = !newTubeCodeList.contains(tubeCode);
      if (removed) {
        removedList.add(tubeCode);
      }
    });
    return removedList;
  }

  public ArrayList<String> getNewAliquots(ArrayList<String> currentAliquotCodeList) {
    ArrayList<String> newList = new ArrayList<>();
    currentAliquotCodeList.forEach(aliquotCode -> {
      boolean isNew = !this.aliquotCodeList.contains(aliquotCode);
      if (isNew) {
        newList.add(aliquotCode);
      }
    });
    return newList;
  }

  public ArrayList<String> getNewTubes(ArrayList<String> currentTubeCodeList) {
    ArrayList<String> newList = new ArrayList<>();
    currentTubeCodeList.forEach(tubeCode -> {
      boolean isNew = !this.tubeCodeList.contains(tubeCode);
      if (isNew) {
        newList.add(tubeCode);
      }
    });
    return newList;
  }
}
