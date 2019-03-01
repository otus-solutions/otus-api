package org.ccem.otus.service.surveyGroup;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;

import java.util.List;

public interface SurveyGroupService {

    List<SurveyGroup> getListOfSurveyGroups();

    ObjectId addNewGroup(String surveyGroup) throws ValidationException;

    String updateGroupName(String groupNameOrigin, String groupNameAltered) throws DataNotFoundException, ValidationException;

    String updateGroupSurveyAcronyms(String surveyGroupJson) throws DataNotFoundException, ValidationException;

    void deleteGroup(String surveyGroupName) throws DataNotFoundException;

    List<SurveyGroup> getSurveyGroupsByUser(String userEmail);
}