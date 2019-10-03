package org.ccem.otus.model.dataSources;

import org.bson.Document;

import java.util.ArrayList;

public abstract class ReportDataSource<T> {
	private String key;
	private String dataSource;
	private String label;
	private ArrayList<T> result = new ArrayList<>();
	private Boolean optional = false;

	public abstract void addResult(T result);

	public abstract ArrayList<Document> buildQuery(Long recruitmentNumber);

	public ArrayList<T> getResult() {
		return result;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public Boolean getOptional() {
		return optional;
	}

	public void setResult(ArrayList<T> result) {
		this.result = result;
	}
}
