package org.ccem.otus.service.extraction.preprocessing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.DataSourceService;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionRecordsFactory;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.item.questions.AutocompleteQuestion;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;


public class AutocompleteQuestionPreProcessor implements ActivityPreProcessor {

    @Inject
    private DataSourceService dataSourceService;

    private static final String QUESTION = "AutocompleteQuestion";

    public SurveyActivityExtractionRecordsFactory process(SurveyForm surveyForm, SurveyActivityExtractionRecordsFactory recordsFactory) throws DataNotFoundException {
        SurveyActivityExtractionRecordsFactory result = recordsFactory;
        for(SurveyItem interator : surveyForm.getSurveyTemplate().itemContainer){
            if(interator.objectType.equals(QUESTION)){
                AutocompleteQuestion autocompleteQuestion = (AutocompleteQuestion) interator;
                Object questionResponse = result.getSurveyInformation().get(autocompleteQuestion.customID);
                if(questionResponse != null){
                    String value = questionResponse.toString();
                    if (!value.equals(null) && !value.equals("")){
                        ArrayList<String> strings = new ArrayList<>();
                        strings.add("medicamentos");
                        String extractionValue = dataSourceService.getElementExtractionValue(autocompleteQuestion.getDataSources(),value);
                        result.getSurveyInformation().replace(autocompleteQuestion.customID, extractionValue);
                    }
                }
            }
        }

        return result;
    }
}
