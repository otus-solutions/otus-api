package br.org.otus.extraction;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.api.ExtractionServiceBean;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExtractionFacade.class)
public class ExtractionFacadeTest {
		
	private static final SurveyTemplate surveyTemplate = new SurveyTemplate();

	private static final String userEmail = "otus@otus.com";
	
	@InjectMocks
	private ExtractionFacade extractionFacade;
	
	@Mock
	private ActivityFacade activityFacade;

	@Mock
	private SurveyFacade surveyFacade;

	@Mock
	private ExtractionServiceBean extractionService;
	
	@Mock
	SurveyActivityExtraction extractor;
	
	
	SurveyForm surveyForm = new SurveyForm(surveyTemplate, userEmail);
	
	private static final String id = "ANTC";
	private static final Integer version = 1;
	private static final ArrayList<SurveyForm> surveys = new ArrayList<>();
	
	@Before
	public void setUp() throws Exception {
		surveys.add(surveyForm);
		surveys.add(surveyForm);
		PowerMockito.when(surveyFacade.findByAcronymWithVersion(id, version)).thenReturn(surveys);
		PowerMockito.when(activityFacade.getAllByID(id)).thenReturn(new ArrayList<>());
		PowerMockito.whenNew(SurveyActivityExtraction.class).withAnyArguments().thenReturn(extractor);
	}

	@Test
	public void should_return_newExtraction() throws Exception {
		assertNotNull(extractionFacade);
		extractionFacade.createActivityExtraction(id, version);
		Mockito.verify(activityFacade).getAllByID(id);
		Mockito.verify(surveyFacade).findByAcronymWithVersion(id, version);
		Mockito.verify(extractionService).createExtraction(extractor);
	}

}
