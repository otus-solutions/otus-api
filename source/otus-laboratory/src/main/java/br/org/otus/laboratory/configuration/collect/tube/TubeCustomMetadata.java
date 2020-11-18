package br.org.otus.laboratory.configuration.collect.tube;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class TubeCustomMetadata {

  private ObjectId _id;
  private String objectType;
  private String type;
  private String value;
  private String extractionValue;

  public static String serialize(TubeCustomMetadata laboratory) {
    Gson builder = TubeCustomMetadata.getGsonBuilder();
    return builder.toJson(laboratory);
  }

  public static TubeCustomMetadata deserialize(String laboratoryJson) {
    Gson builder = TubeCustomMetadata.getGsonBuilder();
    return builder.fromJson(laboratoryJson, TubeCustomMetadata.class);
  }

  public static Gson getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder.create();
  }
}
