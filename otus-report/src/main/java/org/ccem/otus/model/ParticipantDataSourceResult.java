package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

public class ParticipantDataSourceResult {
    private Long recruitmentNumber = new Long(543535);
    private String name;
    private String sex;
    private ImmutableDate birthdate;
    private FieldCenter fieldCenter;

    public static String serialize(ParticipantDataSourceResult participantDataSourceResult) {
        return getGsonBuilder().create().toJson(participantDataSourceResult);
    }

    public static ParticipantDataSourceResult deserialize(String DataSource) {
        GsonBuilder builder = new GsonBuilder();
        return builder.create().fromJson(DataSource, ParticipantDataSourceResult.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
