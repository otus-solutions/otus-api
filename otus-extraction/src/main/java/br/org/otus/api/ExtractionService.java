package br.org.otus.api;

import java.util.List;

import br.org.otus.service.CsvWriterService;

public class ExtractionService {

	private CsvWriterService csvWriterService;

	public ExtractionService() {
		csvWriterService = new CsvWriterService();
	}

	public byte[] createExtraction(Extractable extractionInterface) {
		// TODO: Os métodos iram retornar todas as informações prontas?
		csvWriterService.writeHeader(extractionInterface.getHeaders());
		for (List<Object> values : extractionInterface.getValues()) {
			csvWriterService.writeValues(values);
		}
		return csvWriterService.getResult();
	}
}
