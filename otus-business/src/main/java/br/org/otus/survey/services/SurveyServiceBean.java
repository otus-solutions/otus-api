package br.org.otus.survey.services;

import br.org.otus.survey.SurveyDaoBean;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.survey.form.SurveyForm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Stateless
public class SurveyServiceBean implements SurveyService {

    @Inject
    private SurveyDaoBean surveyDaoBean;
    @Inject
    private SurveyValidatorService surveyValidatorService;

    @Override
    public SurveyForm saveSurvey(SurveyForm survey) throws DataNotFoundException, AlreadyExistException {
        surveyValidatorService.validateSurvey(surveyDaoBean, survey);

        SurveyForm lastVersionSurvey = surveyDaoBean.getLastVersionByAcronym(survey.getSurveyTemplate().identity.acronym);

        if (lastVersionSurvey != null) {
            try {
                discardSurvey(lastVersionSurvey);
            } catch (DataNotFoundException e) {
                throw e;
            }
            survey.setVersion(lastVersionSurvey.getVersion() + 1);
        } else {
            survey.setVersion(1);
        }

        ObjectId persist = surveyDaoBean.persist(survey);
        survey.setSurveyID(persist);

        return survey;
    }

    @Override
    public List<SurveyForm> listUndiscarded() {
        return surveyDaoBean.findUndiscarded();
    }

    @Override
    public List<SurveyForm> findByAcronym(String acronym) {
        return surveyDaoBean.findByAcronym(acronym);
    }

    @Override
    public boolean updateLastVersionSurveyType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto) throws ValidationException {
        if (updateSurveyFormTypeDto.isValid()) {
            return surveyDaoBean.updateLastVersionSurveyType(updateSurveyFormTypeDto.acronym,
                    updateSurveyFormTypeDto.newSurveyFormType.toString());
        } else {
            throw new ValidationException();
        }
    }

    @Override
    public boolean deleteLastVersionByAcronym(String acronym) throws ValidationException, DataNotFoundException {
        if (acronym == null || acronym.isEmpty()) {
            throw new ValidationException();
        } else {
            try {
                return surveyDaoBean.deleteLastVersionByAcronym(acronym);
            } catch (DataNotFoundException e) {
                throw e;
            }
        }
    }

    @Override
    public List<Integer> listSurveyVersions(String acronym) {
        List<Integer> surveyVersions = surveyDaoBean.getSurveyVersions(acronym);
        Collections.reverse(surveyVersions);
        return surveyVersions;
    }

    private void discardSurvey(SurveyForm survey) throws DataNotFoundException {
        try {
            surveyDaoBean.discardSurvey(survey.getSurveyID());
        } catch (DataNotFoundException e) {
            throw e;
        }
        survey.setDiscarded(true);
    }

}
