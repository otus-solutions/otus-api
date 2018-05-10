package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import java.util.List;

public interface ActivityService {

	String create(SurveyActivity surveyActivity);

	SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException;

	List<SurveyActivity> list(long rn);

	SurveyActivity getByID(String id) throws DataNotFoundException;
	
	List<SurveyActivity> getActivitiesToExtraction(String id) throws DataNotFoundException;

        List<SurveyActivity> getAllByIDWithVersion(String id, Integer version) throws DataNotFoundException;
}
