package br.org.otus.survey.services;

import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import com.mongodb.client.result.UpdateResult;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.survey.form.SurveyForm;
import org.json.JSONException;

import java.util.List;

public interface SurveyService {

  SurveyForm saveSurvey(SurveyForm survey) throws DataNotFoundException, AlreadyExistException;

  List<SurveyForm> listUndiscarded(String userEmail);

  List<SurveyForm> listAllUndiscarded();

  List<SurveyForm> findByAcronym(String acronym);

  SurveyForm get(String acronym, Integer version) throws DataNotFoundException;

  Boolean updateLastVersionSurveyType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto)
      throws ValidationException, DataNotFoundException;

  Boolean deleteLastVersionByAcronym(String acronym) throws ValidationException, DataNotFoundException;

  List<Integer> listSurveyVersions(String acronym);

  List<String> listAcronyms();

  void createSurveyJumpMap(SurveyForm surveyForm);

  UpdateResult updateSurveyRequiredExternalID(String surveyID, String requiredExternalID) throws JSONException, DataNotFoundException;
}
