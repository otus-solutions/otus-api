package org.ccem.otus.service.surveyGroup;


import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.model.survey.group.dto.UpdateSurveyGroupNameDto;

import java.util.List;

public interface SurveyGroupService {

    List<SurveyGroup> getListOfSurveyGroups();

    ObjectId addNewSurveyGroup(String surveyGroup) throws ValidationException;

    String updateSurveyGroupName(UpdateSurveyGroupNameDto updateSurveyGroupNameDto) throws ValidationException, DataNotFoundException;

    String updateSurveyGroupAcronyms(String surveyGroupJson) throws DataNotFoundException, ValidationException;

    void deleteSurveyGroup(UpdateSurveyGroupNameDto updateSurveyGroupNameDto) throws DataNotFoundException;

    List<SurveyGroup> getSurveyGroupsByUser(String userEmail);
}