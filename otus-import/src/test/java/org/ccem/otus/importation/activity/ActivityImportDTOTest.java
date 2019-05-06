package org.ccem.otus.importation.activity;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ActivityImportDTOTest {
    @InjectMocks
    private ActivityImportDTO activityImportDTO;
    private String activityImportDTOJson;
    private String surveyActivities = "{\"_id\" : {\"$oid\":\"5a33cb4928f10d1043710f75\"},\"objectType\" : \"Activity\",\"surveyForm\" : {\"sender\" : \"\"," +
            "\"sendingDate\" :\"\",\"objectType\" : \"SurveyForm\",\"surveyFormType\" : \"FORM_INTERVIEW\"}";
    private List<SurveyActivity> listSurveyActivity;
    @Mock
    private SurveyActivity activityDeserialize;

    @Before
    public void setUp() throws Exception {
        activityImportDTO = new ActivityImportDTO();
    }

    @Test
    public void deserialize() {
//        assertTrue(ActivityImportDTO.deserialize(surveyActivities) instanceof ActivityImportDTO);
    }

    @Test
    public void getActivityList() {
    }
}