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

import javax.inject.Inject;
import java.util.List;

public class SurveyFacade {

    @Inject
    private SurveyService surveyService;

    public List<SurveyForm> listUndiscarded() {
        return surveyService.listUndiscarded();
    }

    public List<SurveyForm> findByAcronym(String acronym) {
        return surveyService.findByAcronym(acronym);
    }

    public SurveyForm publishSurveyTemplate(SurveyTemplate surveyTemplate, String userEmail) {
        SurveyForm s = new SurveyForm(surveyTemplate, userEmail);
        try {
            return surveyService.saveSurvey(s);
        } catch (DataNotFoundException | AlreadyExistException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public boolean updateSurveyFormType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto) {
        try {
            return surveyService.updateSurveyFormType(updateSurveyFormTypeDto);
        } catch (ValidationException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build());
        }
    }

    public boolean deleteLastVersionByAcronym(String acronym) {
        try {
            return surveyService.deleteLastVersionByAcronym(acronym);
        } catch (ValidationException | DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build());
        }
    }

    public List<Integer> listVersions(String acronym) {
        return surveyService.listSurveyVersions(acronym);
    }

}