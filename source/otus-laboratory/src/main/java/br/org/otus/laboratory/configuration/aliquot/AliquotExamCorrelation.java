package br.org.otus.laboratory.configuration.aliquot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AliquotExamCorrelation {

  @SerializedName("_id")
  private ObjectId id;
  private String objectType;
  private ArrayList<AliquotPossibleExams> aliquots;

  public Map getHashMap() {
    Map<String, ArrayList<String>> map = new HashMap<>();

    this.aliquots.forEach(aliquotPossibleExams -> map.put(aliquotPossibleExams.getName(), aliquotPossibleExams.getExams()));

    return map;
  }

  public static String serialize(AliquotExamCorrelation laboratory) {
    Gson builder = AliquotExamCorrelation.getGsonBuilder();
    return builder.toJson(laboratory);
  }

  public static AliquotExamCorrelation deserialize(String laboratoryJson) {
    Gson builder = AliquotExamCorrelation.getGsonBuilder();
    return builder.fromJson(laboratoryJson, AliquotExamCorrelation.class);
  }

  public static Gson getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder.create();
  }
}
