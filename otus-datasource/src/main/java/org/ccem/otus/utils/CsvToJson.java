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

        duplicatedHashSet.clear();
        extractionValuesHashSet.clear();

        while (scanner.hasNext()) {
            JsonObject jsonObject = new JsonObject();
            String line = scanner.nextLine();
            String[] fields = line.split(delimiter);

            String value = fields[0].replaceAll("^\"", "");
            value = value.replaceAll("\"$", "");
            jsonObject.addProperty("value", value);

            if(fields.length == 2){
                String extractionValue = fields[1].replaceAll("^\"", "");
                extractionValue = extractionValue.replaceAll("\"$", "");
                jsonObject.addProperty("extractionValue", extractionValue);
                if (!((String) fields[1]).equals(" ")){
                  if(!(extractionValuesHashSet.add(fields[1]))) {
                    this.duplicatedElements.add("extractionValue: "+fields[1]);
                  }
                }
            } else {
                jsonObject.addProperty("extractionValue", "");
            }

            if (!duplicatedHashSet.add(fields[0])){
                this.duplicatedElements.add("value: "+fields[0]);
            }


            elements.add(jsonObject);
        }
    }

}
