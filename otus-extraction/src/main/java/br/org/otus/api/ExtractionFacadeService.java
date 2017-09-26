package br.org.otus.api;

import org.ccem.otus.service.extraction.ExtractionInterface;

import br.org.otus.service.ExtractionService;

public class ExtractionFacadeService {

	private ExtractionService extractionService;

	public ExtractionFacadeService() {
		extractionService = new ExtractionService();
	}

	public void createExtraction(String fileName, ExtractionInterface extractionInterface) {
		extractionService.createExtraction(fileName, extractionInterface);
	}
}
