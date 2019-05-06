package org.ccem.otus.importation.activity.service;

import org.ccem.otus.importation.service.ImportServiceBean;
import org.ccem.otus.persistence.SurveyJumpMapDao;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class ActivityImportValidationServiceBeanTest {

    @InjectMocks
    private ActivityImportValidationService activityImportValidationService;
    @Mock
    private SurveyJumpMapDao surveyJumpMapDao;

    @Test
    public void validateActivity() {
    }
}