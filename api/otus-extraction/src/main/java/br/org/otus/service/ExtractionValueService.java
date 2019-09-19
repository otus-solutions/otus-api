package br.org.otus.service;

import java.util.HashMap;
import java.util.Map;

public class ExtractionValueService {

	private Map<String, Object> records;

	public ExtractionValueService() {
		this.records = new HashMap<String, Object>();
	}

	public void addObjectInRecords(String key, Object value) {
		this.records.put(key, value);
	}

	public void joinValues(Map<String, Object> others) {
		this.records.putAll(others);
	}

	public Map<String, Object> getRecords() {
		return records;
	}

	public void setRecords(Map<String, Object> records) {
		this.records = records;
	}
}
