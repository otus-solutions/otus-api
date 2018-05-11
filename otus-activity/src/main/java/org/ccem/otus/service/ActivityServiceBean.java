package org.ccem.otus.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.persistence.ActivityDao;

@Stateless
public class ActivityServiceBean implements ActivityService {

	@Inject
	private ActivityDao activityDao;

	@Override
	public String create(SurveyActivity surveyActivity) {
		ObjectId objectId = activityDao.persist(surveyActivity);
		return objectId.toString();
	}

	@Override
	public SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException {
		return activityDao.update(surveyActivity);
	}

	@Override
	public List<SurveyActivity> list(long rn) {
		return activityDao.find(rn);
		
	}

	@Override
	public SurveyActivity getByID(String id) throws DataNotFoundException {
		return activityDao.findByID(id);
	}

	@Override
	public List<SurveyActivity> get(String acronym, Integer version) throws DataNotFoundException{
		return activityDao.getUndiscarded(acronym, version);
	}
}
