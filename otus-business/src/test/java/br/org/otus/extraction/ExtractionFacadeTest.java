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

import br.org.otus.api.ExtractionService;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExtractionFacade.class, SurveyFacade.class})
public class ExtractionFacadeTest {
	
	
	private static final SurveyTemplate surveyTemplate = new SurveyTemplate();

	private static final String userEmail = "otus@otus.com";
	
	
	private ExtractionFacade extractionFacade;
	
	@Mock
	private ActivityFacade activityFacade;

	@Mock
	private SurveyFacade surveyFacade;

	@Mock
	private ExtractionService extractionService;
	
	
	SurveyActivityExtraction extractor;
	
	@Mock
	private ExtractionFacade facade;
	
	SurveyForm surveyForm = new SurveyForm(surveyTemplate, userEmail);
	
	private static final String id = "ANTC";
	private static final Integer version = 1;
	private static final ArrayList<SurveyForm> surveys = new ArrayList<>();
	

	
	
	
	
	@Before
	public void setUp() throws Exception {
		surveys.add(surveyForm);
		PowerMockito.whenNew(ExtractionService.class).withNoArguments().thenReturn(extractionService);
		PowerMockito.whenNew(ActivityFacade.class).withNoArguments().thenReturn(activityFacade);
		PowerMockito.whenNew(SurveyFacade.class).withNoArguments().thenReturn(surveyFacade);
		PowerMockito.whenNew(SurveyForm.class).withArguments(surveyTemplate, userEmail).thenReturn(surveyForm);
		PowerMockito.when(surveyFacade.findByAcronymWithVersion(id, version)).thenReturn(surveys);
		PowerMockito.when(activityFacade.getAllByID(id)).thenReturn(new ArrayList<>());
		PowerMockito.whenNew(SurveyActivityExtraction.class).withAnyArguments().thenReturn(extractor);
		PowerMockito.when(extractionService.createExtraction(extractor)).thenReturn(null);
		extractionFacade = PowerMockito.spy(new ExtractionFacade());


	}

	@Test
	public void should_return_newExtractionFacade() throws Exception {
		assertNotNull(extractionFacade);
	}

}
