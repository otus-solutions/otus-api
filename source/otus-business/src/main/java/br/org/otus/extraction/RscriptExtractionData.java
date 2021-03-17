package br.org.otus.extraction;


import com.google.gson.GsonBuilder;


public class RscriptExtractionData {
    private String name;
    private String script;

    public static RscriptExtractionData deserialize(String json) {
        RscriptExtractionData rscriptJson = RscriptExtractionData.getGsonBuilder().create().fromJson(json, RscriptExtractionData.class);
        return rscriptJson;
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }

    public String getName(){
        return name;
    }
}
