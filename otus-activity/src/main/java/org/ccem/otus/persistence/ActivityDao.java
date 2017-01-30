package org.ccem.otus.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;

public interface ActivityDao {

	List<SurveyActivity> find(long rn);

	ObjectId persist(SurveyActivity surveyActivity);

	SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException;

	SurveyActivity findByID(String id) throws DataNotFoundException;

}
