package org.ccem.otus.model.survey.group.dto;

import org.ccem.otus.exceptions.Dto;

public class UpdateSurveyGroupNameDto implements Dto {

    private String surveyGroupName;

    private String newSurveyGroupName;

    public String getSurveyGroupName() {
        return surveyGroupName;
    }

    public void setSurveyGroupName(String oldSurveyGroupName) {
        this.surveyGroupName = oldSurveyGroupName;
    }

    public String getNewSurveyGroupName() {
        return newSurveyGroupName;
    }

    public void setNewSurveyGroupName(String newSurveyGroupName) {
        this.newSurveyGroupName = newSurveyGroupName;
    }

    @Override
    public Boolean isValid() {
        return (surveyGroupName != null && !surveyGroupName.isEmpty()
                && newSurveyGroupName != null && !newSurveyGroupName.isEmpty());
    }
}
