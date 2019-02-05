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
    private HashSet<String> duplicatedElements = new HashSet<>();

    public JsonArray getElements(){
        return elements;
    }

    public HashSet<String> getDuplicatedElements(){
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

        HashSet<String> duplicatedHashSet = new HashSet<>();
        HashSet<String> extractionValuesHashSet = new HashSet<>();

        while (scanner.hasNext()) {
            JsonObject jsonObject = new JsonObject();
            String line = scanner.nextLine();
            String[] fields = line.split(delimiter);
            jsonObject.addProperty("value", fields[0]);
            jsonObject.addProperty("extractionValue", fields[1]);

            if (!duplicatedHashSet.add(fields[0])){
                duplicatedElements.add("value: "+fields[0]);
            }
            if(!(extractionValuesHashSet.add(fields[1]))) {
                duplicatedElements.add("extractionValue: "+fields[1]);
            }

            elements.add(jsonObject);
        }
    }

}
