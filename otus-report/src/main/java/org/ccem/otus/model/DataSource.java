package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

public class DataSource {
    private String key;
    private String dataSource;
    private String birthdate;


    public String getDataSource() {
        return dataSource;
    }

    public static DataSource deserialize(String DataSource) {
        GsonBuilder builder = new GsonBuilder();
        return builder.create().fromJson(DataSource, DataSource.class);
    }
}
