package br.org.otus.model.pendency;

import com.google.gson.GsonBuilder;

public class ActivityInfo {

  private Long recruitmentNumber;
  private String acronym;
  private String name;
  private String lastStatusName;
  private String lastStatusDate;
  private String externalID;

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getAcronym() {
    return acronym;
  }

  public String getName() { return name; }

  public String getExternalID() { return externalID; }

  public String getLastStatusName() { return lastStatusName; }

  public String getLastStatusDate() { return lastStatusDate; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ActivityInfo other = (ActivityInfo) o;
    return (recruitmentNumber == other.getRecruitmentNumber() &&
            acronym.equals(other.getAcronym()));
  }

  public static String serialize(ActivityInfo activityInfo) {
    return (new GsonBuilder()).create().toJson(activityInfo);
  }

  public static ActivityInfo deserialize(String activityInfoJson) {
    return (new GsonBuilder()).create().fromJson(activityInfoJson, ActivityInfo.class);
  }

}
