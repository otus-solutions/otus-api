package br.org.otus.importation.activity;

import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.importation.activity.ActivityImportDTO;
import org.ccem.otus.importation.service.ImportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class ActivityImportationFacadeTest {
    private static final String ACRONYM = "ACTA";
    private static final int VERSION = 2;
    private static final String IMPORT_ACTIVITY_JSON = "{\"objectType\" : \"Activity\",\"surveyForm\" : {\"sender\" : \"\"," +
            "\"sendingDate\" :\"\",\"objectType\" : \"SurveyForm\",\"surveyFormType\" : \"FORM_INTERVIEW\"}}";
    @InjectMocks
    private ActivityImportationFacade activityImportationFacade;

    @Mock
    private ImportService importService;
    @Mock
    private ActivityImportDTO surveyActivities;

    private DataNotFoundException e = spy(new DataNotFoundException());

    @Test
    public void importActivities_method_should_invoke_importActivities_of_ImportService() throws DataNotFoundException {
        surveyActivities = ActivityImportDTO.deserialize(IMPORT_ACTIVITY_JSON);
        activityImportationFacade.importActivities(ACRONYM,VERSION, surveyActivities);
        verify(importService, times(1)).importActivities(ACRONYM,VERSION, surveyActivities);
    }

    @Test (expected = HttpResponseException.class)
    public void importActivities_method_should_throw_DataNotFoundException() throws DataNotFoundException {
        when(importService.importActivities(ACRONYM,VERSION, surveyActivities)).thenThrow(e);
    }
}