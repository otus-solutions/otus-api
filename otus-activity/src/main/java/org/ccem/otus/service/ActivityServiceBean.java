package org.ccem.otus.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.permission.Permission;
import org.ccem.otus.persistence.ActivityDao;
import org.ccem.otus.service.permission.PermissionService;

@Stateless
public class ActivityServiceBean implements ActivityService {

	@Inject
	private ActivityDao activityDao;
	
	@Inject
	private PermissionService permissionService;

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
	    List<Permission> permissions = permissionService.list();	    
		return activityDao.find(rn);
		
	}

	@Override
	public SurveyActivity getByID(String id) throws DataNotFoundException {
		return activityDao.findByID(id);
	}

	@Override
	public List<SurveyActivity> get(String acronym, Integer version) throws DataNotFoundException, MemoryExcededException{
		return activityDao.getUndiscarded(acronym, version);
	}
}
