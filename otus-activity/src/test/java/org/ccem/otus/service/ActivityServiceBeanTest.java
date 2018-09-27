package org.ccem.otus.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.persistence.ActivityDao;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceBeanTest {
	private static final String HASH = "58c83f502226685b94f8973a";
	private static final long RECRUIMENT_NUMBER = 12345;
	private static final String SURVEY_ID = "123456789";
	private static final Integer VERSION = 1;
	@InjectMocks
	private ActivityServiceBean service;
	@Mock
	private ActivityDao activityDao;
	@Mock
	private SurveyActivity Dieta;
	private ObjectId objectId;

	@Before
	public void setup() {
		objectId = new ObjectId(HASH);
		Mockito.when(activityDao.persist(Dieta)).thenReturn(objectId);		
		ArrayList<SurveyActivity> activities = new ArrayList<SurveyActivity>();
		activities.add(Dieta);		
		Mockito.when(activityDao.find(RECRUIMENT_NUMBER)).thenReturn(activities);
	}
	@Test
	public void method_create_should_call_ActivityDao_persist_method_with_a_mySurveyActivity() {
		service.create(Dieta);
		Mockito.verify(activityDao).persist(Dieta);
	}
	@Test
	public void should_return_mySurveyActivity() {
		assertEquals(HASH, service.create(Dieta));
	}
	
//	@Test
//	@Ignore
//	public void method_list_should_call_ActivityDao_find(){
//		service.list(RECRUIMENT_NUMBER);
//		Mockito.verify(activityDao).find(RECRUIMENT_NUMBER);
//	}
	@Test
	public void method_getByID_should_call_ActivityDao_findByID() throws DataNotFoundException{
		service.getByID(SURVEY_ID);
		Mockito.verify(activityDao).findByID(SURVEY_ID);
	}
	
	@Test
	public void method_get_should_call_ActivityDao_getUndiscarded() throws DataNotFoundException, InterruptedException, MemoryExcededException{
		service.get(SURVEY_ID, VERSION);
		Mockito.verify(activityDao).getUndiscarded(SURVEY_ID, VERSION);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = DataNotFoundException.class)
	public void method_get_should_throw_DataNotFound_when_getUndiscarded_not_found() throws DataNotFoundException, InterruptedException, MemoryExcededException{
		Mockito.when(activityDao.getUndiscarded(SURVEY_ID, VERSION)).thenThrow(DataNotFoundException.class);
		service.get(SURVEY_ID, VERSION);
		Mockito.verify(activityDao).getUndiscarded(SURVEY_ID, VERSION);
	}
}
