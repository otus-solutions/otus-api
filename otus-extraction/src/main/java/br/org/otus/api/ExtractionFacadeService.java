package br.org.otus.api;

import java.util.ArrayList;

import br.org.otus.service.ExtractionService;
import br.org.otus.service.HeaderService;

public class ExtractionFacadeService {

	private ExtractionService extractionService;
	private HeaderService headerService;

	public ExtractionFacadeService() {
		extractionService = new ExtractionService();
		headerService = new HeaderService();
	}

	public void singleSelectionQuestion(String id, Integer answer) {
		headerService.addHeader(id);
		extractionService.parseToRecord(answer);
	}

	public void checkboxQuestion(ArrayList<String> id, ArrayList<Boolean> answer) {

	}

}
