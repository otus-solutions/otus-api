package br.org.otus.survey.services;

import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.persistence.SurveyDao;
import org.ccem.otus.survey.form.SurveyForm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Stateless
public class SurveyServiceBean implements SurveyService {

    @Inject
    private SurveyDao surveyDao;
    @Inject
    private SurveyValidatorService surveyValidatorService;

    @Override
    public SurveyForm saveSurvey(SurveyForm survey) throws DataNotFoundException, AlreadyExistException {
        surveyValidatorService.validateSurvey(surveyDao, survey);

        SurveyForm lastVersionSurvey = surveyDao.getLastVersionByAcronym(survey.getSurveyTemplate().identity.acronym);

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

        ObjectId persist = surveyDao.persist(survey);
        survey.setSurveyID(persist);

        return survey;
    }

    @Override
    public List<SurveyForm> listUndiscarded() {
        return surveyDao.findUndiscarded();
    }

    @Override
    public List<SurveyForm> findByAcronym(String acronym) {
        return surveyDao.findByAcronym(acronym);
    }
    
    @Override
    public SurveyForm get(String acronym, Integer version) throws DataNotFoundException {
        return surveyDao.get(acronym, version);
    }

    @Override
    public boolean updateLastVersionSurveyType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto) throws ValidationException {
        if (updateSurveyFormTypeDto.isValid()) {
            return surveyDao.updateLastVersionSurveyType(updateSurveyFormTypeDto.acronym,
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
                return surveyDao.deleteLastVersionByAcronym(acronym);
            } catch (DataNotFoundException e) {
                throw e;
            }
        }
    }

    @Override
    public List<Integer> listSurveyVersions(String acronym) {
        List<Integer> surveyVersions = surveyDao.getSurveyVersions(acronym);
        Collections.reverse(surveyVersions);
        return surveyVersions;
    }

    private void discardSurvey(SurveyForm survey) throws DataNotFoundException {
        try {
            surveyDao.discardSurvey(survey.getSurveyID());
        } catch (DataNotFoundException e) {
            throw e;
        }
        survey.setDiscarded(true);
    }

}
