package org.ccem.otus.utils;

import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class DataSourceValuesMapping {

    private HashMap<String,ValuesMapping> mappingList;

    public class ValuesMapping {
        private String dataSource;
        private HashMap<String, String> hashMap;

        public void setDataSource(String dataSource) {
            this.dataSource = dataSource;
        }

        public void setHashMap(HashMap<String, String> hashMap) {
            this.hashMap = hashMap;
        }
    }

    public String getExtractionValue(String dataSource,String value){
        return mappingList.get(dataSource).hashMap.get(value);
    }

    public static DataSourceValuesMapping deserialize(String DataSource) {
        GsonBuilder builder = DataSourceValuesMapping.getGsonBuilder();
        return builder.create().fromJson(DataSource, DataSourceValuesMapping.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
