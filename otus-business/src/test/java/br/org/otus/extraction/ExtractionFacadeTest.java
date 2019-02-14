package br.org.otus.extraction;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
import br.org.otus.laboratory.extraction.LaboratoryExtraction;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
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
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Mock
  private ExtractionServiceBean extractionService;
  @Mock
  private SurveyActivityExtraction surveyActivityExtraction;
  @Mock
  private LaboratoryExtraction laboratoryExtraction;

  SurveyForm surveyForm = new SurveyForm(surveyTemplate, userEmail);

  private static final String acronym = "ANTC";
  private static final Integer version = 1;
  private static final ArrayList<SurveyForm> surveys = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    surveys.add(surveyForm);
    surveys.add(surveyForm);
    PowerMockito.when(surveyFacade.get(acronym, version)).thenReturn(surveys.get(0));
    PowerMockito.when(activityFacade.get(acronym, version)).thenReturn(new ArrayList<>());
    PowerMockito.whenNew(SurveyActivityExtraction.class).withAnyArguments().thenReturn(surveyActivityExtraction);
    PowerMockito.whenNew(LaboratoryExtraction.class).withAnyArguments().thenReturn(laboratoryExtraction);
  }

  @Test
  public void createActivityExtraction_should_return_newExtraction() throws Exception {
    assertNotNull(extractionFacade);
    extractionFacade.createActivityExtraction(acronym, version);

    Mockito.verify(activityFacade).get(acronym, version);
    Mockito.verify(surveyFacade).get(acronym, version);
    Mockito.verify(extractionService).createExtraction(surveyActivityExtraction);
  }

  @Test
  public void listSurveyVersions_should_call_listVersions_from_surveyFacade() throws Exception {
    assertNotNull(extractionFacade);
    extractionFacade.listSurveyVersions(acronym);

    Mockito.verify(surveyFacade).listVersions(acronym);
  }
  
  @Test
  public void createLaboratoryExtraction_method_should_call_getLaboratoryExtraction_method() throws Exception {
    assertNotNull(extractionFacade);
    extractionFacade.createLaboratoryExtraction();

    Mockito.verify(participantLaboratoryFacade).getLaboratoryExtraction();
  }

  @Test
  public void createLaboratoryExtraction_method_should_call_createExtraction_method_with_new_extraction() throws DataNotFoundException {
    assertNotNull(extractionFacade);
    extractionFacade.createLaboratoryExtraction();

    Mockito.verify(participantLaboratoryFacade).getLaboratoryExtraction();
    Mockito.verify(extractionService).createExtraction(laboratoryExtraction);
  }

}
