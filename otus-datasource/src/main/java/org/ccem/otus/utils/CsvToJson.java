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
		this.scanner.useDelimiter(delimiter);
	}

	public JsonArray execute() {
		JsonArray elements = new JsonArray();

		while (scanner.hasNext()) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("value", scanner.next());
			elements.add(jsonObject);
		}

		return elements;
	}
	
}
