package br.org.otus.extraction;

import br.org.otus.service.ExtractionService;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.service.extraction.Extractable;

import javax.inject.Inject;
import java.util.List;

public class ExtractionFacadeService {
	
	@Inject
	private ActivityFacade activityFacade;  
	@Inject
	private ExtractionService extractionService;

	public ExtractionFacadeService() {
		extractionService = new ExtractionService();
		activityFacade = new ActivityFacade();
	}	

	public void createActivityExtraction(String id) {
		List<? extends Extractable> activities = activityFacade.getAllByID(id);
		extractionService.createExtraction(id, activities);
	}
}
