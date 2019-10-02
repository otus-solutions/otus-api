package org.ccem.otus.model.dataSources.activity;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.FillContainer;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.utils.AnswerAdapter;

import java.util.Map;

public class AnswerFillingDataSourceResult {

    private FillContainer fillContainer;
    private SurveyForm surveyForm;

    public void a(SurveyForm surveyForm) {
        String value = "customID";
        Map<String, String> templateToCustomIdMap = surveyForm.getSurveyTemplate().mapTemplateAndCustomIDS();

        String templateID = templateToCustomIdMap
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

    }

    public static String serialize(AnswerFillingDataSourceResult answerFillingDataSourceResult) {
        return getGsonBuilder().create().toJson(answerFillingDataSourceResult);
    }

    public static AnswerFillingDataSourceResult deserialize(String DataSource) {
        GsonBuilder builder = AnswerFillingDataSourceResult.getGsonBuilder();
        return builder.create().fromJson(DataSource, AnswerFillingDataSourceResult.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = SurveyForm.getGsonBuilder();
//        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AnswerFill.class, new AnswerAdapter());
        return builder;
    }

    public FillContainer getFillContainer() {
        return fillContainer;
    }

    public SurveyForm getSurveyForm() {
        return surveyForm;
    }
}
