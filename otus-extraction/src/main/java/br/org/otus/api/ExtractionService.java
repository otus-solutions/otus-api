package br.org.otus.api;

import br.org.otus.service.CsvWriterService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public class ExtractionService {

	private CsvWriterService csvWriterService;

	public ExtractionService() {
		csvWriterService = new CsvWriterService();
	}

	public byte[] createExtraction(Extractable extractionInterface) throws DataNotFoundException {
		csvWriterService.write(extractionInterface.getHeaders(), extractionInterface.getValues());
		return csvWriterService.getResult();
	}
}
