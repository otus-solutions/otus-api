package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;

import java.util.List;

public interface ActivityDao {

	List<SurveyActivity> find(long rn);

	ObjectId persist(SurveyActivity surveyActivity);

	SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException;

	SurveyActivity findByID(String id) throws DataNotFoundException;

	List<SurveyActivity> findAllByID(String id) throws DataNotFoundException;

	List<SurveyActivity> findByCategory(String categoryName);

	void updateCategory(ActivityCategory activityCategory);

	List<SurveyActivity> findAllByIDWithVersion(String id, Integer version) throws DataNotFoundException;
}
