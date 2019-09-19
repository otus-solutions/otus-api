package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class CheckerUpdatedDTO {

    private String id;
    private ActivityStatus activityStatus;

    public String getId() {
        return id;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public static String serialize(CheckerUpdatedDTO  checkerUpdatedDTO) {
        return CheckerUpdatedDTO.getGsonBuilder().create().toJson(checkerUpdatedDTO);
    }

    public static CheckerUpdatedDTO deserialize(String checkerUpdatedJson) {
        CheckerUpdatedDTO  checkerUpdatedDTO = CheckerUpdatedDTO.getGsonBuilder().create().fromJson(checkerUpdatedJson, CheckerUpdatedDTO.class);
        return checkerUpdatedDTO;
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return builder;
    }
}


