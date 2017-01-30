package org.ccem.otus.service;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;

public interface ActivityService {

	String create(SurveyActivity surveyActivity);

	SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException;

	List<SurveyActivity> list(long rn);

	SurveyActivity getByID(String id) throws DataNotFoundException;

}
