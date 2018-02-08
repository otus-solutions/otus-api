package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

public class DataSourceModel {
    private String dataSource;
    private String rn;
    private String birthdate;
    private String acronym;
    private String name;
    private String fieldCenter;


    public String getDataSource() {
        return dataSource;
    }

    public static DataSourceModel deserialize(String DataSource) {
        GsonBuilder builder = new GsonBuilder();
        return builder.create().fromJson(DataSource, DataSourceModel.class);
    }
}
