package br.org.otus.laboratory.configuration.collect.tube;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class TubeCustomMetadata {

  public static final String OBJECT_TYPE = "TubeCustomMetadata";

  @SerializedName("_id")
  private ObjectId id;
  private String objectType;
  private String type;
  private String value;
  private String extractionValue;

  public ObjectId getId() {
    return id;
  }

  public String getObjectType() {
    return objectType;
  }

  public String getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  public String getExtractionValue() {
    return extractionValue;
  }

  public static String serialize(TubeCustomMetadata laboratory) {
    return getGsonBuilder().create().toJson(laboratory);
  }

  public static TubeCustomMetadata deserialize(String laboratoryJson) {
    return getGsonBuilder().create().fromJson(laboratoryJson, TubeCustomMetadata.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }
}
