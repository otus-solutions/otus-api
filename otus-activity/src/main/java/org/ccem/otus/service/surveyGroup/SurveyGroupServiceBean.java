package org.ccem.otus.service.surveyGroup;

import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.persistence.SurveyGroupDao;

import javax.inject.Inject;
import java.util.List;


public class SurveyGroupServiceBean implements SurveyGroupService {

    @Inject
    private SurveyGroupDao surveyGroupDao;

    @Override
    public List<SurveyGroup> getListOfSurveyGroups() {
        return surveyGroupDao.getListOfSurveyGroups();
    }

    @Override
    public ObjectId addNewGroup(String surveyGroupJson) throws ValidationException {
        SurveyGroup surveyGroup = SurveyGroup.deserialize(surveyGroupJson);
        surveyGroupNameInvalid(surveyGroup);
        surveyGroupNameConflits(surveyGroup.getName());
        return surveyGroupDao.persist(surveyGroup);
    }


    @Override
    public String updateGroupSurveyAcronyms(String surveyGroupJson) throws DataNotFoundException {
        SurveyGroup surveyGroupAltered = SurveyGroup.deserialize(surveyGroupJson);
        surveyGroupNameExists(surveyGroupAltered.getName());
        return surveyGroupDao.updateGroupSurveyAcronyms(surveyGroupAltered);
    }

    @Override
    public String updateGroupName(String oldGroupName, String alteredGroupName) throws DataNotFoundException, ValidationException {
        surveyGroupNameExists(oldGroupName);
        surveyGroupNameConflits(alteredGroupName);
        return surveyGroupDao.updateGroupName(oldGroupName, alteredGroupName);
    }

    @Override
    public void deleteGroup(String surveyGroupName) throws DataNotFoundException {
        DeleteResult result = surveyGroupDao.deleteGroup(surveyGroupName);
        if (result.getDeletedCount() == 0) throw new DataNotFoundException(new Throwable("SurveyGroup not found"));
    }

    @Override
    public List<SurveyGroup> getSurveyGroupsByUser(String userEmail) {
        return surveyGroupDao.getSurveyGroupsByUser(userEmail);
    }

    private void surveyGroupExists(SurveyGroup surveyGroup) throws ValidationException {
        surveyGroupDao.findSurveyGroupNameConflits(surveyGroup.getName());
    }

    private void surveyGroupNameInvalid(SurveyGroup surveyGroup) throws ValidationException {
        if (surveyGroup.getName() == null) {
            throw new ValidationException(new Throwable("surveyGroupName with invalid value"));
        }
    }
//
//    private void surveyGroupIDvalid(ObjectId surveyGroupID) throws DataNotFoundException {
//        surveyGroupDao.findSurveyGroupById(surveyGroupID);
//    }

//    private void surveyGroupIDvalid(ObjectId surveyGroupID) throws DataNotFoundException {
//        surveyGroupDao.findSurveyGroupById(surveyGroupID);
//    }

    private void surveyGroupNameExists(String surveyGroupName) throws DataNotFoundException {
        surveyGroupDao.findSurveyGroupByName(surveyGroupName);
    }

//    private void surveyGroupNameUpdateConflits(SurveyGroup surveyGroup) throws ValidationException, DataNotFoundException {
//        SurveyGroup surveyGroupOrigin = surveyGroupDao.findSurveyGroupById(surveyGroup.getSurveyGroupID());
//        if (!surveyGroupOrigin.getName().equals(surveyGroup.getName())) {
//            surveyGroupExists(surveyGroup);
//        }
//    }

    private void surveyGroupNameConflits(String surveyGroupName) throws ValidationException {
        surveyGroupDao.findSurveyGroupNameConflits(surveyGroupName);
    }
}