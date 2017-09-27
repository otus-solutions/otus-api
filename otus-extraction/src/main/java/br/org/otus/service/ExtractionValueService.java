package br.org.otus.service;

import java.util.ArrayList;
import java.util.List;

public class ExtractionValueService {

	private List<Object> records;

	public ExtractionValueService() {
		setRecords(new ArrayList<Object>());
	}

	public void addObjectInRecords(Object object) {
		this.records.add(object);
	}

	public void joinValues(List<Object> others) {
		this.records.addAll(others);
	}

	public List<Object> getRecords() {
		return records;
	}

	public void setRecords(List<Object> records) {
		this.records = records;
	}
}
