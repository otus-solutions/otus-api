package br.org.otus.survey.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.persistence.SurveyDao;
import org.ccem.otus.persistence.SurveyJumpMapDao;
import org.ccem.otus.survey.form.SurveyForm;

import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import org.json.JSONException;
import org.json.JSONObject;

@Stateless
public class SurveyServiceBean implements SurveyService {

  @Inject
  private SurveyDao surveyDao;
  @Inject
  private SurveyJumpMapDao surveyJumpMapDao;
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
  public List<SurveyForm> listUndiscarded(String userEmail) {
    return this.listUndiscarded(new ArrayList<>(), userEmail);
  }

  private List<SurveyForm> listUndiscarded(List<String> permittedSurveys, String userEmail) {
    return surveyDao.findUndiscarded(permittedSurveys, userEmail);
  }

  @Override
  public List<SurveyForm> findByAcronym(String acronym) {
    return surveyDao.findByAcronym(acronym);
  }

  @Override
  public List<SurveyForm> listAllUndiscarded() {
    return surveyDao.findAllUndiscarded();
  }

  @Override
  public SurveyForm get(String acronym, Integer version) throws DataNotFoundException {
    return surveyDao.get(acronym, version);
  }

  @Override
  public Boolean updateLastVersionSurveyType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto) throws ValidationException, DataNotFoundException {
    if (updateSurveyFormTypeDto.isValid()) {
      try {
        return surveyDao.updateLastVersionSurveyType(updateSurveyFormTypeDto.acronym, updateSurveyFormTypeDto.newSurveyFormType.toString());
      } catch (DataNotFoundException e) {
        throw e;
      }
    } else {
      throw new ValidationException("Invalid UpdateSurveyFormTypeDto");
    }
  }

  @Override
  public Boolean deleteLastVersionByAcronym(String acronym) throws ValidationException, DataNotFoundException {
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

  @Override
  public List<String> listAcronyms() {
    List<String> surveys = surveyDao.listAcronyms();
    return surveys;
  }

  @Override
  public void createSurveyJumpMap(SurveyForm surveyForm) {
    SurveyJumpMap surveyJumpMap = surveyDao.createJumpMap(surveyForm.getSurveyTemplate().identity.acronym,surveyForm.getVersion());
    surveyJumpMap.setSurveyOid(surveyForm.getSurveyID());
    surveyJumpMap.setSurveyAcronym(surveyForm.getSurveyTemplate().identity.acronym);
    surveyJumpMap.setSurveyVersion(surveyForm.getVersion());
    surveyJumpMapDao.persist(surveyJumpMap);
  }

  @Override
  public UpdateResult updateSurveyRequiredExternalID(String surveyID, String requiredExternalID) throws JSONException, DataNotFoundException {
    ObjectId objectId = new ObjectId(surveyID);
    JSONObject requiredExternalIDJsonObject = new JSONObject(requiredExternalID);
    Boolean stateRequiredExternalID = (Boolean) requiredExternalIDJsonObject.get("requiredExternalID");
    return surveyDao.updateSurveyRequiredExternalID(objectId, stateRequiredExternalID);
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