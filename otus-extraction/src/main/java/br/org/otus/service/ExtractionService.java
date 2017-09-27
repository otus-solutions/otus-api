package br.org.otus.service;

import java.io.File;
import java.util.List;

import org.ccem.otus.service.extraction.Extractable;

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

	public void createExtraction(String fileName, List<? extends Extractable> extractionInterface) {
		file = new File(fileName);
		// TODO: 26/09/17 iterate over List
//		extractionHeaderService.setHeader(extractionInterface.getHeaders());
//		extractionValueService.setRecords(extractionInterface.getValues());
		csvWriterService.write(file, extractionHeaderService, extractionValueService);
	}

}
