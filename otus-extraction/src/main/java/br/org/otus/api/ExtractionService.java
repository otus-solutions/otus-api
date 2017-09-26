package br.org.otus.api;

import br.org.otus.service.ExtractionHeaderService;
import br.org.otus.service.ExtractionValueService;

public class ExtractionService {

	private ExtractionValueService extractionValueService;
	private ExtractionHeaderService extractionHeaderService;

	public ExtractionService() {
		extractionHeaderService = new ExtractionHeaderService();
		extractionValueService = new ExtractionValueService();
	}

	public void createExtraction() {
		
	}

}
