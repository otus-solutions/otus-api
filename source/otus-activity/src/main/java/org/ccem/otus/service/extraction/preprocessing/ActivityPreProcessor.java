package org.ccem.otus.service.extraction.preprocessing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionRecordsFactory;
import org.ccem.otus.survey.form.SurveyForm;

public interface ActivityPreProcessor {

    SurveyActivityExtractionRecordsFactory process(SurveyForm surveyForm, SurveyActivityExtractionRecordsFactory recordsFactory) throws DataNotFoundException;

}
