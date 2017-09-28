package br.org.otus.service;

import org.ccem.otus.service.extraction.Extractable;

import java.io.File;

public class ExtractionService {

	private File file;
	private CsvWriterService csvWriterService;
	private ExtractionValueService extractionValueService;
	private ExtractionHeaderService extractionHeaderService;

	public ExtractionService() {
		extractionHeaderService = new ExtractionHeaderService();
		extractionValueService = new ExtractionValueService();
	}

	public void createExtraction(String fileName, Extractable extractionInterface) {
		file = new File(fileName);
		csvWriterService = new CsvWriterService(file);
		// TODO:
	}

}
