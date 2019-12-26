package br.org.otus.model.pendency;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

public class ActivityInfo {

  @Equalization(name = "id")
  private String id;

  @Equalization(name = "acronym")
  private String acronym;

  @Equalization(name = "recruitmentNumber")
  private int recruitmentNumber;

  @Equalization(name = "lastStatusName")
  private String lastStatusName;

  @Equalization(name = "externalID")
  private String externalID;

  public ActivityInfo() {
    this.id = null;
    this.acronym = "";
    this.recruitmentNumber = 0;
    this.lastStatusName = "";
    this.externalID = "";
  }

  public String getId() {
    return id;
  }

  public String getAcronym() {
    return acronym;
  }

  public int getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getLastStatusName() {
    return lastStatusName;
  }

  public String getExternalID() {
    return externalID;
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
