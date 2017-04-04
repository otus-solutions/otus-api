package br.org.otus.configuration.datasource;

import javax.ws.rs.FormParam;

public class DataSourceFormPOJO {

	@FormParam("id")
	private String id;

	@FormParam("name")
	private String name;

	@FormParam("file")
	private byte[] file;

	@FormParam("delimiter")
	private String delimiter;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public byte[] getFile() {
		return file;
	}

	public String getDelimiter() {
		return delimiter;
	}

}
