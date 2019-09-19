package org.ccem.otus.service.extraction;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionHeadersFactory;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionRecordsFactory;
import org.ccem.otus.service.extraction.preprocessing.ActivityPreProcessor;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SurveyActivityExtraction.class})
public class SurveyActivityExtractionTest {

    private SurveyActivityExtraction surveyActivityExtraction;
    private List<SurveyActivity> surveyActivities;

    @Mock
    private ActivityPreProcessor activityPreProcessor;
    @Mock
    private SurveyActivityExtractionRecordsFactory records;
    @Mock
    private SurveyActivityExtractionHeadersFactory headers;
    @Mock
    private SurveyTemplate surveyTemplate;
    @Mock
    private SurveyActivity surveyActivity;

    private static final String USER_EMAIL = "otus@tus.com";
    private SurveyForm surveyForm = PowerMockito.spy(new SurveyForm(surveyTemplate, USER_EMAIL));

    @Before
    public void setUp() throws Exception {
        PowerMockito.whenNew(SurveyActivityExtractionRecordsFactory.class).withAnyArguments().thenReturn(records);
        PowerMockito.whenNew(SurveyActivityExtractionHeadersFactory.class).withAnyArguments().thenReturn(headers);
        surveyActivities = Arrays.asList(surveyActivity);
        surveyActivityExtraction = new SurveyActivityExtraction(surveyForm, surveyActivities);
        PowerMockito.when(activityPreProcessor.process(Mockito.any(), Mockito.any())).thenReturn(records);
    }

    @Test
    public void should_method_getValues_constructor_values_for_extraction() throws Exception {
        surveyActivityExtraction.addPreProcessor(activityPreProcessor);
        List<List<Object>> values = surveyActivityExtraction.getValues();
        Mockito.verify(activityPreProcessor, Mockito.times(1)).process(Mockito.any(),Mockito.any());

    }
}