package org.ccem.otus.model.survey.group.dto;

import org.ccem.otus.exceptions.Dto;

public class UpdateSurveyGroupNameDto implements Dto {

    private String oldSurveyGroupName;
    private String newSurveyGroupName;

    public String getOldSurveyGroupName() {
        return oldSurveyGroupName;
    }

    public void setOldSurveyGroupName(String oldSurveyGroupName) {
        this.oldSurveyGroupName = oldSurveyGroupName;
    }

    public String getNewSurveyGroupName() {
        return newSurveyGroupName;
    }

    public void setNewSurveyGroupName(String newSurveyGroupName) {
        this.newSurveyGroupName = newSurveyGroupName;
    }

    @Override
    public Boolean isValid() {
        return (oldSurveyGroupName != null && !oldSurveyGroupName.isEmpty()
                && newSurveyGroupName != null && !newSurveyGroupName.isEmpty());
    }
}
