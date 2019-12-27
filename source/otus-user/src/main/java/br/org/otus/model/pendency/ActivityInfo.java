package br.org.otus.model.pendency;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

public class ActivityInfo {

  @Equalization(name = "id")
  private ObjectId id;

  @Equalization(name = "acronym")
  private String acronym;

  @Equalization(name = "recruitmentNumber")
  private int recruitmentNumber;

  public ObjectId getId() {
    return id;
  }

  public String getAcronym() {
    return acronym;
  }

  public int getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public static String serialize(ActivityInfo surveyGroup) {
    return getGsonBuilder().create().toJson(surveyGroup);
  }

  public static ActivityInfo deserialize(String surveyGroupJson) {
    return ActivityInfo.getGsonBuilder().create().fromJson(surveyGroupJson, ActivityInfo.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    return builder;
  }

}
