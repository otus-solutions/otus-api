package org.ccem.otus.service.surveyGroup;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.json.JSONException;

import java.util.List;

public interface SurveyGroupService {

    List<SurveyGroup> getListOfSurveyGroups();

    ObjectId addNewSurveyGroup(String surveyGroup) throws ValidationException;

    //String updateSurveyGroupName(String oldGroupName, String newGroupName) throws DataNotFoundException, ValidationException;

    String updateSurveyGroupName(String surveyGroupNamesUpdate) throws JSONException, DataNotFoundException, ValidationException;

    String updateSurveyGroupAcronyms(String surveyGroupJson) throws DataNotFoundException, ValidationException;

    void deleteSurveyGroup(String surveyGroupName) throws DataNotFoundException;

    List<SurveyGroup> getSurveyGroupsByUser(String userEmail);
}