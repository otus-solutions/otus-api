package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.List;

public class TubeCollectionData {

  private String objectType;
  private boolean isCollected;
  private String metadata;
  private List<ObjectId> customMetadata;
  private String operator;
  private LocalDateTime time;
  private String dynamicMetadata;

  public TubeCollectionData() {
    this.objectType = "TubeCollectionData";
    this.isCollected = false;
    this.metadata = "";
    this.operator = "";
    this.dynamicMetadata = "";
  }

  public String getObjectType() {
    return objectType;
  }

  public boolean isCollected() {
    return isCollected;
  }

  public String getMetadata() {
    return metadata;
  }

  public List<ObjectId> getCustomMetadata() {
    return customMetadata;
  }

  public String getOperatorEmail() {
    return operator;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public String getDynamicMetadata() {
    return dynamicMetadata;
  }

  public static String serialize(TubeCollectionData tubeCollectionData) {
    GsonBuilder builder = ParticipantLaboratory.getGsonBuilder();
    return builder.create().toJson(tubeCollectionData);
  }

  public static TubeCollectionData deserialize(String tubeJson) {
    return getGsonBuilder().create().fromJson(tubeJson, TubeCollectionData.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.serializeNulls();
    return builder;
  }
}
