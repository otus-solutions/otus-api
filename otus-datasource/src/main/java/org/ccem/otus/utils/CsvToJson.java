package org.ccem.otus.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CsvToJson {

	private static final String CHARSET_UTF_8 = "UTF-8";
	
	private Scanner scanner;

	public CsvToJson(String delimiter, byte[] bytes) {
		if(bytes == null ||  delimiter == null) {
			throw new IllegalArgumentException();
		}
		InputStream is = new ByteArrayInputStream(bytes);
		this.scanner = new Scanner(is, CHARSET_UTF_8);
		//TODO: não será necessário, avaliar e remover
//		this.scanner.useDelimiter(delimiter);
	}

	public JsonArray execute(String delimiter) {
		JsonArray elements = new JsonArray();

		while (scanner.hasNext()) {
			JsonObject jsonObject = new JsonObject();
			String line = scanner.nextLine();
			String[] fields = line.split(delimiter);
			jsonObject.addProperty("value", fields[0]);
			jsonObject.addProperty("extractionValue", fields[1]);
			elements.add(jsonObject);
		}

		return elements;
	}
	
}
