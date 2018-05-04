package br.org.otus.survey.services;

import br.org.otus.survey.SurveyDao;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.survey.form.SurveyForm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SurveyServiceBean implements SurveyService {

    @Inject
    private SurveyDao surveyDao;
    @Inject
    private SurveyValidorService surveyValidorService;

    @Override
    public SurveyForm saveSurvey(SurveyForm survey) throws DataNotFoundException {
//        surveyValidorService.validateSurvey(surveyDao, survey); todo: uncomment and fix

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
    public boolean updateSurveyFormType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto) throws ValidationException {
        if (updateSurveyFormTypeDto.isValid()) {
            return surveyDao.updateSurveyFormType(updateSurveyFormTypeDto.acronym,
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

    public void discardSurvey(SurveyForm survey) throws DataNotFoundException {
        try {
            surveyDao.discardSurvey(survey.getSurveyID());
        } catch (DataNotFoundException e) {
            throw e;
        }
        survey.setDiscarded(true);
    }

}
