package br.org.otus.service;

import java.util.List;

public class HeaderService {

	private List<String> header;

	public void addHeader(String column) {
		header.add(column);
	}

	public Boolean removeHeader(String column) {
		return header.remove(column);
	}

	public List<String> getHeader() {
		return header;
	}

}
