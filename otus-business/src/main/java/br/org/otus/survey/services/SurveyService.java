package br.org.otus.survey.services;

import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.survey.form.SurveyForm;

import java.util.List;

public interface SurveyService {

	SurveyForm saveSurvey(SurveyForm survey) throws DataNotFoundException, AlreadyExistException;
	
	List<SurveyForm> listUndiscarded();
	
	List<SurveyForm> findByAcronym(String acronym);

	boolean updateSurveyFormType(UpdateSurveyFormTypeDto updateSurveyFormTypeDto) throws ValidationException;

	boolean deleteLastVersionByAcronym(String acronym) throws ValidationException, DataNotFoundException;

	List<Integer> listSurveyVersions(String acronym);
}
