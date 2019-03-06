package org.ccem.otus.service.surveyGroup;

import com.mongodb.client.result.DeleteResult;
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
    public ObjectId addNewSurveyGroup(String surveyGroupJson) throws ValidationException {
        verifySurveyGroupJsonValid(surveyGroupJson);
        SurveyGroup surveyGroup = SurveyGroup.deserialize(surveyGroupJson);
        verifySurveyGroupNameValid(surveyGroup);
        verifySurveyGroupNameConflits(surveyGroup.getName());
        return surveyGroupDao.persist(surveyGroup);
    }

    @Override
    public String updateSurveyGroupAcronyms(String surveyGroupJson) throws DataNotFoundException {
        SurveyGroup surveyGroupAltered = SurveyGroup.deserialize(surveyGroupJson);
        verifySurveyGroupNameExists(surveyGroupAltered.getName());
        return surveyGroupDao.updateSurveyGroupAcronyms(surveyGroupAltered);
    }

    @Override
    public String updateSurveyGroupName(String oldSurveyGroupName, String newSurveyGroupName) throws DataNotFoundException, ValidationException {
        verifySurveyGroupNameExists(oldSurveyGroupName);
        verifyNewSurveyGroupName(newSurveyGroupName);
        verifySurveyGroupNameConflits(newSurveyGroupName);
        return surveyGroupDao.updateGroupName(oldSurveyGroupName, newSurveyGroupName);
    }

    @Override
    public void deleteSurveyGroup(String surveyGroupName) throws DataNotFoundException {
        DeleteResult result = surveyGroupDao.deleteSurveyGroup(surveyGroupName);
        if (result.getDeletedCount() == 0) throw new DataNotFoundException(new Throwable("SurveyGroup not found"));
    }

    @Override
    public List<SurveyGroup> getSurveyGroupsByUser(String userEmail) {
        return surveyGroupDao.getSurveyGroupsByUser(userEmail);
    }

    private void verifySurveyGroupJsonValid(String surveyGroupJson) throws ValidationException {
        if (surveyGroupJson.isEmpty()) {
            throw new ValidationException(new Throwable("invalid surveyGroupJson"));
        }
    }

    private void verifySurveyGroupNameValid(SurveyGroup surveyGroup) throws ValidationException {
        //if (surveyGroup.getName() == null || surveyGroup.getName().isEmpty() || !(surveyGroup.getName().matches("^[A-Za-z0-9]*$"))) {
        if (surveyGroup.getName() == null || surveyGroup.getName().isEmpty()){
            throw new ValidationException(new Throwable("surveyGroupName with invalid value"));
        }
    }

    private void verifySurveyGroupNameExists(String surveyGroupName) throws DataNotFoundException {
        surveyGroupDao.findSurveyGroupByName(surveyGroupName);
    }

    private void verifyNewSurveyGroupName(String newSurveyGroupName) throws ValidationException {
        if (!newSurveyGroupName.matches("^[A-Za-z0-9]*$")) {
            throw new ValidationException(new Throwable("invalid newSurveyGroupName"));
        }
    }

    private void verifySurveyGroupNameConflits(String surveyGroupName) throws ValidationException {
        surveyGroupDao.findSurveyGroupNameConflits(surveyGroupName);
    }
}