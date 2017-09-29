package br.org.otus.service;

import org.ccem.otus.service.extraction.Extractable;

public class ExtractionService {

	private CsvWriterService csvWriterService;

	public ExtractionService() {
		csvWriterService = new CsvWriterService();
	}

	public byte[] createExtraction(Extractable extractionInterface) {
		// TODO: in method getValues the return is all values to write? good question.
//		csvWriterService.writeHeader(extractionInterface.getHeaders());
		csvWriterService.writeValues(extractionInterface.getValues());
		return csvWriterService.getResultSet();
	}
}
