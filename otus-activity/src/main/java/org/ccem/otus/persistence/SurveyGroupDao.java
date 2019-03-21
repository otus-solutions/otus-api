package org.ccem.otus.persistence;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;

import com.mongodb.client.result.DeleteResult;

public interface SurveyGroupDao {
    List<SurveyGroup> getListOfSurveyGroups();

    Document findSurveyGroupByName(String name) throws DataNotFoundException;

    void findSurveyGroupNameConflits(String name) throws ValidationException;

    ObjectId persist(SurveyGroup surveyGroup);

    String updateSurveyGroupAcronyms(SurveyGroup surveyGroup);

    String updateSurveyGroupName(String originalName, String updateName);

    DeleteResult deleteSurveyGroup(String surveyGroupName) throws DataNotFoundException;

    List<SurveyGroup> getSurveyGroupsByUser(String userEmail);

    List<String> getOrphanSurveys();

    List<String> getUserPermittedSurveys(List<String> surveyGroups);
}