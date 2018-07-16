package br.org.otus.laboratory.project.transportation.persistence;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class WorkAliquotFiltersDTO {

    private String code;
    private String initialDate;
    private String finalDate;
    private String fieldCenter;
    private String role;
    private ArrayList aliquotList;

    public String getCode() {
        return code;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public String getFieldCenter() {
        return fieldCenter;
    }

    public String getRole() {
        return role;
    }

    public ArrayList getAliquotList() {
        return aliquotList;
    }


    public static String serialize(WorkAliquotFiltersDTO participantDataSourceResult) {
        return getGsonBuilder().create().toJson(participantDataSourceResult);
    }

    public static WorkAliquotFiltersDTO deserialize(String DataSource) {
        GsonBuilder builder = WorkAliquotFiltersDTO.getGsonBuilder();
        return builder.create().fromJson(DataSource, WorkAliquotFiltersDTO.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
