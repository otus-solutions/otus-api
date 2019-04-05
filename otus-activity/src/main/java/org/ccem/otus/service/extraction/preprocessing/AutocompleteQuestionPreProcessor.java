package org.ccem.otus.service.extraction.preprocessing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.DataSourceService;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionRecordsFactory;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.item.questions.AutocompleteQuestion;

import javax.inject.Inject;
import java.util.ArrayList;


public class AutocompleteQuestionPreProcessor implements ActivityPreProcessor {

    @Inject
    private DataSourceService dataSourceService;

    private static final String QUESTION = "AutocompleteQuestion";

    public SurveyActivityExtractionRecordsFactory process(SurveyForm surveyForm, SurveyActivityExtractionRecordsFactory recordsFactory) throws DataNotFoundException {
        SurveyActivityExtractionRecordsFactory result = recordsFactory;
        for(SurveyItem iterator : surveyForm.getSurveyTemplate().itemContainer){
            if(iterator.objectType.equals(QUESTION)){
                AutocompleteQuestion autocompleteQuestion = (AutocompleteQuestion) iterator;
                Object questionResponse = result.getSurveyInformation().get(autocompleteQuestion.customID);
                if(questionResponse != null){
                    String value = questionResponse.toString();
                    if (!value.equals(null) && !value.equals("")){
                        String extractionValue = dataSourceService.getElementExtractionValue(autocompleteQuestion.getDataSources(),value);
                        result.getSurveyInformation().replace(autocompleteQuestion.customID, extractionValue);
                    }
                }
            }
        }

        return result;
    }
}
