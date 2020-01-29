package org.ccem.otus.model.dataSources.participant;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

public class ParticipantDataSourceResult {
  private Long recruitmentNumber;
  private String name;
  private String sex;
  private ImmutableDate birthdate;
  private FieldCenter fieldCenter;

  public static String serialize(ParticipantDataSourceResult participantDataSourceResult) {
    return getGsonBuilder().create().toJson(participantDataSourceResult);
  }

  public static ParticipantDataSourceResult deserialize(String DataSource) {
    GsonBuilder builder = ParticipantDataSourceResult.getGsonBuilder();
    return builder.create().fromJson(DataSource, ParticipantDataSourceResult.class);
  }

  private static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    return builder;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getName() {
    return name;
  }

  public String getSex() {
    return sex;
  }

  public FieldCenter getFieldCenter() {
    return fieldCenter;
  }

  public ImmutableDate getBirthdate() {
    return birthdate;
  }


}
