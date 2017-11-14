package br.org.otus.service;

import java.util.ArrayList;
import java.util.List;

public class ExtractionHeaderService {

	private List<String> headers;

	public ExtractionHeaderService() {
		headers = new ArrayList<String>();
	}

	public void addColumnInHeader(String column) {
		this.headers.add(column);
	}

	public void setHeader(List<String> header) {
		this.headers = header;
	}

	public void joinHeader(List<String> others) {
		this.headers.addAll(others);
	}

	public Boolean removeHeader(String column) {
		return getHeader().remove(column);
	}

	public List<String> getHeader() {
		return headers;
	}

}
