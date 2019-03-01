package org.ccem.otus.persistence;

import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;

import java.util.List;

public interface SurveyGroupDao {
    List<SurveyGroup> getListOfSurveyGroups();

    Document findSurveyGroupByName(String name) throws DataNotFoundException;

    void findSurveyGroupNameConflits(String name) throws ValidationException;

    ObjectId persist(SurveyGroup surveyGroup);

    SurveyGroup findSurveyGroupById(ObjectId surveyGroupID) throws DataNotFoundException;

    //String updateGroup(SurveyGroup surveyGroup);

    String updateGroupName(String originalName, String updateName);

    DeleteResult deleteGroup(String surveyGroupName) throws DataNotFoundException;


    List<SurveyGroup> getSurveyGroupsByUser(String userEmail);
}