package br.org.otus.service;

import java.io.File;

import org.ccem.otus.service.extraction.ExtractionInterface;

public class ExtractionService {

	private File file;
	private CsvWriterService csvWriterService;
	private ExtractionValueService extractionValueService;
	private ExtractionHeaderService extractionHeaderService;

	public ExtractionService() {
		csvWriterService = new CsvWriterService();
		extractionHeaderService = new ExtractionHeaderService();
		extractionValueService = new ExtractionValueService();
	}

	public void createExtraction(String fileName, List<ExtractionInterface> extractionInterface) {
		file = new File(fileName);
		extractionHeaderService.setHeader(extractionInterface.getHeaders());
		extractionValueService.setRecords(extractionInterface.getValues());
		csvWriterService.write(file, extractionHeaderService, extractionValueService);
	}

}
