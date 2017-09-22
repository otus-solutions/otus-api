package br.org.otus.config;

import java.io.File;

public class FileConfiguration {

	private File file;
	private String fileName;

	public FileConfiguration(String fileName) {
		this.fileName = fileName;
	}

	public File createFile() {
		return file = new File(fileName);
	}

}
