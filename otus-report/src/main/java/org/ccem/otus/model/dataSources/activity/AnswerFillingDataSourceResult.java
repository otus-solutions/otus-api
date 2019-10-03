package org.ccem.otus.model.dataSources.activity;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.utils.AnswerAdapter;

public class AnswerFillingDataSourceResult extends QuestionFill {

    public AnswerFillingDataSourceResult(QuestionFill questionFill) {

    }

    public static String serialize(AnswerFillingDataSourceResult answerFillingDataSourceResult) {
        return getGsonBuilder().create().toJson(answerFillingDataSourceResult);
    }

    public static AnswerFillingDataSourceResult deserialize(String DataSource) {
        GsonBuilder builder = AnswerFillingDataSourceResult.getGsonBuilder();
        AnswerFillingDataSourceResult result = builder.create().fromJson(DataSource, AnswerFillingDataSourceResult.class);

        return result;
    }

    private static GsonBuilder getGsonBuilder() {
//        GsonBuilder builder = SurveyForm.getGsonBuilder();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AnswerFill.class, new AnswerAdapter());
        return builder;
    }
}
