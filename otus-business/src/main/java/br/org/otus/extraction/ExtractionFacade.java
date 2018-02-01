package br.org.otus.extraction;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.survey.form.SurveyForm;

import br.org.otus.api.ExtractionService;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;

public class ExtractionFacade {

	@Inject
	private ActivityFacade activityFacade;

	@Inject
	private SurveyFacade surveyFacade;

	@Inject
	private ExtractionService extractionService;

	public ExtractionFacade() {
		extractionService = new ExtractionService();
		activityFacade = new ActivityFacade();
	}

	public byte[] createActivityExtraction(String id) {
		List<SurveyActivity> activities = activityFacade.getAllByID(id);
		SurveyForm surveyForm = surveyFacade.findByAcronym(id).get(0);  //TODO 04/12/17: implement a findFirst?
		SurveyActivityExtraction extractor = new SurveyActivityExtraction(surveyForm, activities);
		try {
			return extractionService.createExtraction(extractor);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
