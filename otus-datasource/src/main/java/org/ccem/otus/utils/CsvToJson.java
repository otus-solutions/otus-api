package org.ccem.otus.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CsvToJson {

    private static final String CHARSET_UTF_8 = "UTF-8";

    private Scanner scanner;
    private JsonArray elements = new JsonArray();
    private JsonArray duplicatedElements = new JsonArray();

    public JsonArray getElements(){
        return elements;
    }

    public JsonArray getDuplicatedElements(){
        return duplicatedElements;
    }

    public CsvToJson(String delimiter, byte[] bytes) {
        if (bytes == null || delimiter == null) {
            throw new IllegalArgumentException();
        }
        InputStream is = new ByteArrayInputStream(bytes);
        this.scanner = new Scanner(is, CHARSET_UTF_8);
    }

    public void execute(String delimiter) {

        HashSet<String> valuesHashSet = new HashSet<>();
        HashSet<String> extractionValuesHashSet = new HashSet<>();

        while (scanner.hasNext()) {
            JsonObject jsonObject = new JsonObject();
            String line = scanner.nextLine();
            String[] fields = line.split(delimiter);
            jsonObject.addProperty("value", fields[0]);
            jsonObject.addProperty("extractionValue", fields[1]);
            if (!(valuesHashSet.add(fields[0])) || !(extractionValuesHashSet.add(fields[1]))) {
                duplicatedElements.add(jsonObject);
            }
            elements.add(jsonObject);
        }
    }

}
