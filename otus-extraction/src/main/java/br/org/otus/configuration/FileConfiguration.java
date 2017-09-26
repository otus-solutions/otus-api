package br.org.otus.configuration;

import java.io.File;

public class FileConfiguration {

	private File file;
	private String fileName;

	public FileConfiguration(String fileName) {
		this.fileName = fileName;
	}

	public File createFile(String fileName) {
		return file = new File(fileName);
	}

}
