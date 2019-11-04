package br.org.otus.survey.api;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import br.org.otus.survey.services.SurveyService;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.json.JSONException;

import javax.inject.Inject;
import java.util.List;

public class SurveyFacade {

    @Inject
    private SurveyService surveyService;

    public List<SurveyForm> listUndiscarded(String userEmail) {
        return surveyService.listUndiscarded(userEmail);
    }

    public List<SurveyForm> listAllUndiscarded() {
        return surveyService.listAllUndiscarded();
    }

    public List<SurveyForm> findByAcronym(String acronym) {
        return surveyService.findByAcronym(acronym);
    }

    public SurveyForm get(String acronym, Integer version) {
        try {
            return surveyService.get(acronym, version);

        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }

    }

    public SurveyForm publishSurveyTemplate(SurveyTemplate surveyTemplate, String userEmail) {
        SurveyForm s = new SurveyForm(surveyTemplate, userEmail);
        try {
            SurveyForm surveyForm = surveyService.saveSurvey(s);
            surveyService.createSurveyJumpMap(surveyForm);
            return surveyForm;
        } catch (DataNotFoundException | AlreadyExistException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public boolean updateLastVersionSurveyType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto) {
        try {
            return surveyService.updateLastVersionSurveyType(updateSurveyFormTypeDto);
        } catch (ValidationException | DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public boolean deleteLastVersionByAcronym(String acronym) {
        try {
            return surveyService.deleteLastVersionByAcronym(acronym);
        } catch (ValidationException | DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public List<Integer> listVersions(String acronym) {
        return surveyService.listSurveyVersions(acronym);
    }
    public List<String> listAcronyms() {
        return surveyService.listAcronyms();
    }

  public long updateSurveyRequiredExternalID(String surveyID, String requiredExternalID) {
      try {
        return  surveyService.updateSurveyRequiredExternalID(surveyID, requiredExternalID).getModifiedCount();
      } catch (JSONException | DataNotFoundException e){
        throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
      }
  }
}
