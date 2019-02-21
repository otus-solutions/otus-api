package br.org.otus.extraction.rest;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.extraction.ExtractionFacade;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.user.api.UserFacade;

@RunWith(MockitoJUnitRunner.class)
public class ExtractionResourceTest {

  private static final String acronym = "ANTC";
  private static final Integer version = 1;

  @InjectMocks
  private ExtractionResource extractionResource;

  @Mock
  private UserFacade userFacade;
  @Mock
  private ExtractionFacade extractionFacade;
  @Mock
  private SecurityContext securityContext;

  byte[] test;

  @Before
  public void setUp() throws Exception {
    PowerMockito.when(extractionFacade.createActivityExtraction(acronym, version)).thenReturn(null);
  }

  @Test
  public void should_verify_method_createActivityExtraction_have_been_called() throws DataNotFoundException {
    extractionResource.extractActivities(acronym, version);
    Mockito.verify(extractionFacade).createActivityExtraction(acronym, version);
  }

  @Test
  public void should_verify_method_listSurveyVersions_have_been_called() {
    extractionResource.listSurveyVersions(acronym);
    Mockito.verify(extractionFacade).listSurveyVersions(acronym);
  }
  
  @Test
  public void should_verify_method_extractExamsValues_have_been_called() throws DataNotFoundException {
    extractionResource.extractExamsValues();
    Mockito.verify(extractionFacade).createLaboratoryExamsValuesExtraction();
  }

	@Test
	public void should_verify_method_extractAnnexesReport_have_been_called() throws DataNotFoundException {
		extractionResource.extractAnnexesReport(acronym,version);
		Mockito.verify(extractionFacade).createAttachmentsReportExtraction(acronym,version);
	}

  @Test
  public void extractLaboratory_method_should_call_createLaboratoryExtraction_method() throws DataNotFoundException {
    extractionResource.extractLaboratory();
    
    Mockito.verify(extractionFacade).createLaboratoryExtraction();
  }

}
