package org.ccem.otus.model.survey.activity.variables;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class StaticVariableRequestDTO {
    private long recruitmentNumber;
    private ArrayList<StaticVariableRequest> variables;

    public long getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public ArrayList<StaticVariableRequest> getVariablesList() {
        return variables;
    }

    public static String serialize(StaticVariableRequestDTO staticVariableRequestDTO) {
        return StaticVariableRequestDTO.getGsonBuilder().create().toJson(staticVariableRequestDTO);
    }

    public static StaticVariableRequestDTO deserialize(String variablesJson) {
         StaticVariableRequestDTO staticVariableRequestDTO = StaticVariableRequestDTO.getGsonBuilder().create().fromJson(variablesJson, StaticVariableRequestDTO.class);
        return staticVariableRequestDTO;
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        return builder;
    }
}
