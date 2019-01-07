package org.ccem.otus.service.extraction.preprocessing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.DataSourceService;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionRecordsFactory;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.item.SurveyItem;

import javax.inject.Inject;


public class AutocompleteQuestionPreProcessor implements ActivityPreProcessor {

    @Inject
    private DataSourceService dataSourceService;

    private static final String QUESTION = "AutocompleteQuestion";

    public SurveyActivityExtractionRecordsFactory process(SurveyForm surveyForm, SurveyActivityExtractionRecordsFactory recordsFactory) throws DataNotFoundException {
        SurveyActivityExtractionRecordsFactory result = recordsFactory;
        for(SurveyItem iterator : surveyForm.getSurveyTemplate().itemContainer){
            if(iterator.objectType.equals(QUESTION)){
                String value = result.getSurveyInformation().get(iterator.customID).toString();
                if (!value.equals(null) || !value.equals("")){
                    String extractionValue = dataSourceService.getElementDataSource(value).getExtractionValue();
                    result.getSurveyInformation().replace(iterator.customID, extractionValue);
                }
            }
        }

        return result;
    }
}
