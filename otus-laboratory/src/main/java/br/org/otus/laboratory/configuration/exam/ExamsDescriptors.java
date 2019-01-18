package br.org.otus.laboratory.configuration.exam;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ExamsDescriptors {

  @SerializedName("_id")
  private ObjectId id;
  private String objectType;
  private ArrayList<String> descriptions; // deveria garantir unicidade!

  public static String serialize(ExamsDescriptors examsDescriptors) {
    Gson builder = ExamsDescriptors.getGsonBuilder();
    return builder.toJson(examsDescriptors);
  }

  public static ExamsDescriptors deserialize(String examsDescriptors) {
    Gson builder = ExamsDescriptors.getGsonBuilder();
    return builder.fromJson(examsDescriptors, ExamsDescriptors.class);
  }

  public static Gson getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder.create();
  }

  public ArrayList<String> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(ArrayList<String> descriptions) {
    this.descriptions = descriptions;
  }

}
