package org.ccem.otus.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.dto.CheckerUpdatedDTO;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityDao;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CheckerUpdatedDTO.class)
public class ActivityServiceBeanTest {
  private static final String HASH = "58c83f502226685b94f8973a";
  private static final long RECRUIMENT_NUMBER = 12345;
  private static final String SURVEY_ID = "123456789";
  private static final Integer VERSION = 1;
  private static final String USER_EMAIL = "otus@gmail.com";
  private static final String checkerUpdated = "{\"id\":\"5c0e5d41e69a69006430cb75\",\"activityStatus\":{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_OFFLINE\",\"date\":\"2018-12-10T12:33:29.007Z\",\"user\":{\"name\":\"Otus\",\"surname\":\"Solutions\",\"extraction\":true,\"extractionIps\":[\"999.99.999.99\"],\"phone\":\"21987654321\",\"fieldCenter\":{},\"email\":\"otus@gmail.com\",\"admin\":false,\"enable\":true,\"meta\":{\"revision\":0,\"created\":0,\"version\":0},\"$loki\":2}}}";

  @InjectMocks
  private ActivityServiceBean service;
  @Mock
  private ActivityDao activityDao;
  @Mock
  private SurveyActivity Dieta;
  @Mock
  private ActivityAccessPermissionService activityAccessPermissionService;
  private ObjectId objectId;
  @Mock
  private SurveyActivity activity;
  @Mock
  private CheckerUpdatedDTO checkerUpdatedDTO;
  private ActivityAccessPermission permission;
  private List<SurveyActivity> surveyActivities;

  @Before
  public void setup() {
    objectId = new ObjectId(HASH);
    when(activityDao.persist(Dieta)).thenReturn(objectId);
    ArrayList<SurveyActivity> activities = new ArrayList<SurveyActivity>();
    activities.add(Dieta);
    when(activityDao.find(new ArrayList<>(), "", RECRUIMENT_NUMBER)).thenReturn(activities);
  }

  @Test
  public void method_create_should_call_ActivityDao_persist_method_with_a_mySurveyActivity() {
    service.create(Dieta);
    verify(activityDao, times(1)).persist(Dieta);
  }

  @Test
  public void should_return_mySurveyActivity() {
    assertEquals(HASH, service.create(Dieta));
  }

  @Test
  public void listMethod_should_invoke_find_of_ActivityDao_find() throws Exception {
    surveyActivities = service.list(RECRUIMENT_NUMBER, USER_EMAIL);
    assertTrue(surveyActivities.get(0) instanceof SurveyActivity);
    verify(activityDao, times(1)).find(new ArrayList<>(), "", RECRUIMENT_NUMBER);
    verify(activityAccessPermissionService, times(1)).list();
  }

  @Test
  public void method_getByID_should_call_ActivityDao_findByID() throws DataNotFoundException {
    service.getByID(SURVEY_ID);
    verify(activityDao, times(1)).findByID(SURVEY_ID);
  }

  @Test
  public void method_get_should_call_ActivityDao_getUndiscarded()
      throws DataNotFoundException, InterruptedException, MemoryExcededException {
    service.get(SURVEY_ID, VERSION);
    verify(activityDao, times(1)).getUndiscarded(SURVEY_ID, VERSION);
  }

  @SuppressWarnings("unchecked")
  @Test(expected = DataNotFoundException.class)
  public void method_get_should_throw_DataNotFound_when_getUndiscarded_not_found()
      throws DataNotFoundException, InterruptedException, MemoryExcededException {
    when(activityDao.getUndiscarded(SURVEY_ID, VERSION)).thenThrow(DataNotFoundException.class);
    service.get(SURVEY_ID, VERSION);
    verify(activityDao, times(1)).getUndiscarded(SURVEY_ID, VERSION);
  }

  @Test
  public void updateCheckerActivityMethod_should_invoke_updateChekerActivity_of_ActivityDao() throws DataNotFoundException{
    mockStatic(CheckerUpdatedDTO.class);
    when(CheckerUpdatedDTO.deserialize(checkerUpdated)).thenReturn(checkerUpdatedDTO);
    service.updateCheckerActivity(checkerUpdated);
    verify(activityDao, times(1)).updateCheckerActivity(checkerUpdatedDTO);
  }
}
