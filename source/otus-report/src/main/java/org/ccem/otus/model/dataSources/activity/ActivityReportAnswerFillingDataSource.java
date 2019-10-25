package org.ccem.otus.model.dataSources.activity;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.survey.form.SurveyForm;

import java.util.ArrayList;
import java.util.Map;

public class ActivityReportAnswerFillingDataSource extends ReportDataSource<QuestionFill> {

    @Override
    public void addResult(QuestionFill result) {
        super.getResult().add(result);
    }

    @Override
    public ArrayList<Document> buildQuery(Long recruitmentNumber) {
        return null;
    }

    public void fillResult(SurveyActivity activity) {
            ArrayList<QuestionFill> result = getTemplateAndCustomIDS(activity);
            setResult(result);
    }

    public ArrayList<QuestionFill> getResult() {
        return super.getResult();
    }

    public void setResult(ArrayList<QuestionFill> result) {
        super.setResult(result);
    }

    private ArrayList<QuestionFill> getTemplateAndCustomIDS(SurveyActivity activity) {
        SurveyForm surveyForm = activity.getSurveyForm();

        Map<String, String> templateToCustomIdMap = surveyForm.getSurveyTemplate().mapTemplateAndCustomIDS();

        for (QuestionFill questionFill : activity.getFillContainer().getFillingList()) {
            String questionID = questionFill.getQuestionID();
            String customID = templateToCustomIdMap.get(questionID);
            questionFill.setCustomID(customID);
        }

        return (ArrayList<QuestionFill>) activity.getFillContainer().getFillingList();
    }
}