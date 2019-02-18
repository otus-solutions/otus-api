package br.org.otus.extraction;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.service.extraction.preprocessing.AutocompleteQuestionPreProcessor;
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
import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.extraction.ExamUploadExtration;
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
  private ExamUploadFacade examUploadFacade;
  @Mock
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Mock
  private LaboratoryExtraction laboratoryExtraction;
  @Mock
  private AutocompleteQuestionPreProcessor autocompleteQuestionPreProcessor;
  @Mock
  private SurveyActivityExtraction surveyActivityExtraction;
  @Mock
  private ExamUploadExtration examUploadExtration;
  @Mock
  private ExtractionServiceBean extractionService;

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
    PowerMockito.whenNew(ExamUploadExtration.class).withAnyArguments().thenReturn(examUploadExtration);
    PowerMockito.whenNew(LaboratoryExtraction.class).withAnyArguments().thenReturn(laboratoryExtraction);
  }

  @Test
  public void should_return_new_extraction_of_activities() throws Exception {
    assertNotNull(extractionFacade);
    extractionFacade.createActivityExtraction(acronym, version);
    Mockito.verify(activityFacade).get(acronym, version);
    Mockito.verify(surveyFacade).get(acronym, version);
    Mockito.verify(surveyActivityExtraction, Mockito.times(1)).addPreProcessor(autocompleteQuestionPreProcessor);
    Mockito.verify(extractionService).createExtraction(surveyActivityExtraction);
  }

  @Test
  public void should_return_new_extraction_of_exam() throws Exception {
    assertNotNull(extractionFacade);
    extractionFacade.createLaboratoryExamsValuesExtraction();
    Mockito.verify(examUploadFacade).getExamResultsExtractionValues();
    Mockito.verify(extractionService).createExtraction(examUploadExtration);
  }

  @Test
  public void should_call_listVersions_from_surveyFacade() throws Exception {
    assertNotNull(extractionFacade);
    extractionFacade.listSurveyVersions(acronym);
    Mockito.verify(surveyFacade).listVersions(acronym);
  }

  @Test
  public void createAttachmentsReportExtraction_should_call_getAttachmentsReport_from_extractionService() throws DataNotFoundException {
    extractionFacade.createAttachmentsReportExtraction(acronym, version);
    Mockito.verify(extractionService).getAttachmentsReport(acronym, version);
  }

  @Test
  public void createLaboratoryExtraction_method_should_call_createExtraction_method_with_new_extraction() throws DataNotFoundException {
    assertNotNull(extractionFacade);
    extractionFacade.createLaboratoryExtraction();

    Mockito.verify(participantLaboratoryFacade).getLaboratoryExtraction();
    Mockito.verify(extractionService).createExtraction(laboratoryExtraction);
  }

}
