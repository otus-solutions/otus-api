package br.org.otus.api;

import br.org.otus.service.CsvWriterService;

public class ExtractionService {

	private CsvWriterService csvWriterService;

	public ExtractionService() {
		csvWriterService = new CsvWriterService();
	}

	public byte[] createExtraction(Extractable extractionInterface) {
		csvWriterService.write(extractionInterface.getHeaders(), extractionInterface.getValues());
		return csvWriterService.getResult();
	}
}
