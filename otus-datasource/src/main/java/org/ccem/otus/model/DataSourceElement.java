package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

public class DataSourceElement {
    private String value;
    private String extractionValue;

    public DataSourceElement(){}

    public DataSourceElement(String value, String extractionValue){
        this.value = value;
        this.extractionValue = extractionValue;
    }

    public String getValue(){
        return this.value;
    }

    public String getExtractionValue(){
        return this.extractionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSourceElement that = (DataSourceElement) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(extractionValue, that.extractionValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, extractionValue);
    }

    public static String serialize(DataSourceElement dataSourceElement) {
        return getGsonBuilder().create().toJson(dataSourceElement);
    }

    public static DataSourceElement deserialize(String DataSource) {
        GsonBuilder builder = DataSourceElement.getGsonBuilder();
        return builder.create().fromJson(DataSource, DataSourceElement.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
