package br.org.otus.service;

import java.util.ArrayList;
import java.util.List;

public class ExtractionService {

	private List<Object> records;

	public ExtractionService() {
		records = new ArrayList<Object>();
	}

	public void parseToRecord(Object object) {
		records.add(object);
	}

	public List<Object> getRecord() {
		return this.records;
	}
}
