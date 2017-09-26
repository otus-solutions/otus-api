package br.org.otus.service;

import java.util.ArrayList;
import java.util.List;

public class ExtractionValueService {

	private List<Object> records;

	public ExtractionValueService() {
		records = new ArrayList<Object>();
	}

	public void parseToRecord(Object object) {
		records.add(object);
	}

	public List<Object> getRecord() {
		return this.records;
	}
}
