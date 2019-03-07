package br.org.otus.survey.group;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.builders.Security;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.model.survey.group.dto.UpdateSurveyGroupNameDto;
import org.ccem.otus.service.surveyGroup.SurveyGroupService;

import javax.inject.Inject;
import java.util.List;

public class SurveyGroupFacade {

    @Inject
    private SurveyGroupService surveyGroupService;

    public List<SurveyGroup> getListOfSurveyGroups() {
        return surveyGroupService.getListOfSurveyGroups();
    }

    public String addNewSurveyGroup(String surveyGroupJson) {
        try {
            return surveyGroupService.addNewSurveyGroup(surveyGroupJson).toString();
        } catch (ValidationException e) {
            throw new HttpResponseException(
                    Security.Validation.build(e.getCause().getMessage(), e.getData()));
        }
    }

    public String updateSurveyGroupAcronyms(String surveyGroupJson) {
        try {
            return surveyGroupService.updateSurveyGroupAcronyms(surveyGroupJson);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));

        } catch (ValidationException e) {
            throw new HttpResponseException(
                    Security.Validation.build(e.getCause().getMessage(), e.getData()));
        }
    }

    public String updateSurveyGroupName(UpdateSurveyGroupNameDto updateSurveyGroupNameDto) {
        try {
            return surveyGroupService.updateSurveyGroupName(updateSurveyGroupNameDto);
        } catch (ValidationException e) {
            throw new HttpResponseException(
                    Security.Validation.build(e.getCause().getMessage(), e.getData()));
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));

        }
    }

    public void deleteSurveyGroup(String surveyGroupName) {
        try {
            surveyGroupService.deleteSurveyGroup(surveyGroupName);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));

        }
    }

    public List<SurveyGroup> getSurveyGroupsByUser(String userEmail) {
        return surveyGroupService.getSurveyGroupsByUser(userEmail);

    }
}