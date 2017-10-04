package br.org.otus.extraction;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;

import br.org.otus.api.ExtractionService;
import br.org.otus.survey.activity.api.ActivityFacade;

public class ExtractionFacadeService {

	@Inject
	private ActivityFacade activityFacade;
	@Inject
	private ExtractionService extractionService;

	public ExtractionFacadeService() {
		extractionService = new ExtractionService();
		activityFacade = new ActivityFacade();
	}

	public byte[] createActivityExtraction(String id) {
		List<SurveyActivity> activities = activityFacade.getAllByID(id);
		SurveyActivityExtraction extractor = new SurveyActivityExtraction(activities);
		return extractionService.createExtraction(extractor);
	}
}
