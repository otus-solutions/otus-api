package br.org.otus.importation;

import br.org.otus.importation.activity.ActivityImportationFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class ActivityImportationResourceTest {
    private static final String ACRONYM = "ACTA";
    private static final int VERSION = 1;
    private String surveyActivities = "{\"objectType\" : \"Activity\",\"surveyForm\" : {\"sender\" : \"\"," +
            "\"sendingDate\" :\"\",\"objectType\" : \"SurveyForm\",\"surveyFormType\" : \"FORM_INTERVIEW\"}}";

    @InjectMocks
    private ActivityImportationResource activityImportationResource;

    @Mock
    private ActivityImportationFacade activityImportationFacade;

    @Test
    public void importActivities_method_should_invoke_importActivities_of_activityImportationFacade() {
        activityImportationResource.importActivities(surveyActivities,ACRONYM,VERSION);
        verify(activityImportationFacade, times(1)).importActivities(Mockito.anyString(),Mockito.anyInt(),Mockito.anyObject());
    }
}