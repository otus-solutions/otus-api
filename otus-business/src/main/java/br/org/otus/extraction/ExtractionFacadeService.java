package br.org.otus.extraction;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.ExtractionInterface;

import br.org.otus.service.ExtractionService;
import br.org.otus.survey.activity.api.ActivityFacade;

public class ExtractionFacadeService {
	
	@Inject
	private ActivityFacade activityFacade;  

	private ExtractionService extractionService;

	public ExtractionFacadeService() {
		extractionService = new ExtractionService();
		activityFacade = new ActivityFacade();
	}	

	public void createActivityExtraction(String id, ExtractionInterface extractionInterface) {
		List<SurveyActivity> activities = activityFacade.getAllByID(id);
		extractionService.createExtraction(id, activities);
	}
}
