package br.org.otus.extraction;

import br.org.otus.service.ExtractionService;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.Extractable;
import org.ccem.otus.service.extraction.SurveyActivityExtractor;

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
		List<SurveyActivity> activities = activityFacade.getAllByID(id);
		//TODO: criar o response com o retorno de bytes
		SurveyActivityExtractor extractor = new SurveyActivityExtractor(activities);
		extractionService.createExtraction(extractor);
	}
}
