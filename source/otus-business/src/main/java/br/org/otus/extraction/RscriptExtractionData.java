package br.org.otus.extraction;


import com.google.gson.GsonBuilder;
import org.ccem.otus.model.SerializableModel;


public class RscriptExtractionData extends SerializableModel {
    private String name;
    private String script;

    public static RscriptExtractionData deserialize(String json) {
        return (RscriptExtractionData) deserialize(json, RscriptExtractionData.class);
    }

    public String getName(){
        return name;
    }
}
