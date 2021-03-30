package br.org.otus.laboratory.project.transportation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.util.ArrayList;
import java.util.List;

public class TransportMaterialCorrelation {


  private ObjectId _id;
  private ArrayList<String> aliquotCodeList;
  private ArrayList<String> tubeCodeList;
  private ArrayList<ReceivedMaterial> receivedMaterials;

  public TransportMaterialCorrelation(ObjectId transportationLotId, ArrayList<String> aliquotCodeList, ArrayList<String> tubeCodeList) {
    this._id = transportationLotId;
    this.aliquotCodeList = aliquotCodeList;
    this.tubeCodeList = tubeCodeList;
    this.receivedMaterials = new ArrayList<>();
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

  public List<String> getRemovedAliquots(List<String> newAliquotCodeList) {
    List<String> removedList = new ArrayList<>();
    this.aliquotCodeList.forEach(aliquotCode -> {
      if (isNotContains(newAliquotCodeList,aliquotCode)) {
        removedList.add(aliquotCode);
      }
    });
    return removedList;
  }

  public List<String> getRemovedTubes(List<String> newTubeCodeList) {
    List<String> removedList = new ArrayList<>();
    this.tubeCodeList.forEach(tubeCode -> {
      if (isNotContains(newTubeCodeList,tubeCode)) {
        removedList.add(tubeCode);
      }
    });
    return removedList;
  }

  public List<String> getNewAliquots(List<String> currentAliquotCodeList) {
    List<String> newList = new ArrayList<>();
    currentAliquotCodeList.forEach(aliquotCode -> {
      if (isNotContains(this.aliquotCodeList,aliquotCode)) {
        newList.add(aliquotCode);
      }
    });
    return newList;
  }

  public List<String> getNewTubes(List<String> currentTubeCodeList) {
    List<String> newList = new ArrayList<>();
    currentTubeCodeList.forEach(tubeCode -> {
      if (isNotContains(this.tubeCodeList,tubeCode)) {
        newList.add(tubeCode);
      }
    });
    return newList;
  }

  private Boolean isNotContains(List codes,String code){
    return !codes.contains(code);
  }

  public ArrayList<ReceivedMaterial> getReceivedMaterials() {
    return receivedMaterials;
  }
}
