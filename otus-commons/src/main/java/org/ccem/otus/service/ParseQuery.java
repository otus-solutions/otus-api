package org.ccem.otus.service;


import com.google.gson.GsonBuilder;
import org.bson.Document;

public class ParseQuery {

    public static Document toDocument(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }
}