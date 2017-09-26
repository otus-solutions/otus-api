package br.org.otus.api;


public class ExtractionFacadeService {

	private ExtractionService extractionService;

	public ExtractionFacadeService() {
		extractionService = new ExtractionService();
	}

	public void createExtraction() {
		extractionService.createExtraction();
	}

}
