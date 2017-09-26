package br.org.otus.service;

import java.util.List;

public class ExtractionHeaderService {

	private List<String> headers;

	public void addHeader(String column) {
		getHeader().add(column);
	}

	public Boolean removeHeader(String column) {
		return getHeader().remove(column);
	}

	public List<String> getHeader() {
		return headers;
	}

	public void setHeader(List<String> header) {
		this.headers = header;
	}

}
