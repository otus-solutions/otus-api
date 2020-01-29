package org.ccem.otus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.dto.CheckerUpdatedDTO;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityDao;
import org.ccem.otus.persistence.ActivityProgressExtractionDao;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.identity.Identity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CheckerUpdatedDTO.class)
public class ActivityServiceBeanTest {
  private static final String HASH = "58c83f502226685b94f8973a";
  private static final long RECRUIMENT_NUMBER = 12345;
  private static final String SURVEY_ID = "123456789";
  private static final Integer VERSION = 1;
  private static final String USER_EMAIL = "otus@gmail.com";
  private static final String checkerUpdated = "{\"id\":\"5c0e5d41e69a69006430cb75\",\"activityStatus\":{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_OFFLINE\",\"date\":\"2018-12-10T12:33:29.007Z\",\"user\":{\"name\":\"Otus\",\"surname\":\"Solutions\",\"extraction\":true,\"extractionIps\":[\"999.99.999.99\"],\"phone\":\"21987654321\",\"fieldCenter\":{},\"email\":\"otus@gmail.com\",\"admin\":false,\"enable\":true,\"meta\":{\"revision\":0,\"created\":0,\"version\":0},\"$loki\":2}}}";
  private static final String ACRONYM = "ACTA";
  private static final String CATEGORY_NAME = "C0";
  private static final String CENTER = "RS";
  private ActivityAccessPermission permission;
  private List<SurveyActivity> surveyActivities;

  @InjectMocks
  private ActivityServiceBean service;
  @Mock
  private ActivityDao activityDao;
  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private SurveyForm surveyForm;
  @Mock
  private SurveyTemplate surveyTemplate;
  @Mock
  private Identity identity;
  @Mock
  private SurveyActivity surveyActivityToPersist;
  @Mock
  private ActivityAccessPermissionService activityAccessPermissionService;
  private ObjectId objectId;
  @Mock
  private SurveyActivity activity;
  @Mock
  private CheckerUpdatedDTO checkerUpdatedDTO;
  @Mock
  private ActivityProgressExtractionDao activityProgressExtractionDao;

  @Before
  public void setup() {
    objectId = new ObjectId(HASH);
    Whitebox.setInternalState(identity, "acronym", "TST");
    Whitebox.setInternalState(identity, "name", "TEST");
    Whitebox.setInternalState(surveyTemplate, "identity", identity);
    when(activityDao.persist(any())).thenReturn(objectId);
    when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);
    when(surveyForm.getSurveyTemplate()).thenReturn(surveyTemplate);
    ArrayList<SurveyActivity> activities = new ArrayList<SurveyActivity>();
    activities.add(surveyActivity);
    when(activityDao.find(new ArrayList<>(), USER_EMAIL, RECRUIMENT_NUMBER)).thenReturn(activities);
  }

  @Test
  public void method_create_should_call_ActivityDao_persist_method_with_a_mySurveyActivity() {
    service.create(surveyActivity);
    verify(activityDao, times(1)).persist(surveyActivity);
    verify(surveyForm, times(1)).setSurveyTemplate(null);
  }

  @Test
  public void should_return_mySurveyActivity() {
    assertEquals(HASH, service.create(surveyActivity));
  }

  @Test
  public void listMethod_should_invoke_find_of_ActivityDao_find() throws Exception {
    surveyActivities = service.list(RECRUIMENT_NUMBER, USER_EMAIL);
    assertTrue(surveyActivities.get(0) instanceof SurveyActivity);
    verify(activityDao, times(1)).find(new ArrayList<>(), USER_EMAIL, RECRUIMENT_NUMBER);
    verify(activityAccessPermissionService, times(1)).list();
  }

  @Test
  public void method_getByID_should_call_ActivityDao_findByID() throws DataNotFoundException {
    service.getByID(SURVEY_ID);
    verify(activityDao, times(1)).findByID(SURVEY_ID);
  }

  @Test
  public void method_get_should_call_ActivityDao_getUndiscarded() throws DataNotFoundException, InterruptedException, MemoryExcededException {
    service.get(SURVEY_ID, VERSION);
    verify(activityDao, times(1)).getUndiscarded(SURVEY_ID, VERSION);
  }

  @SuppressWarnings("unchecked")
  @Test(expected = DataNotFoundException.class)
  public void method_get_should_throw_DataNotFound_when_getUndiscarded_not_found() throws DataNotFoundException, InterruptedException, MemoryExcededException {
    when(activityDao.getUndiscarded(SURVEY_ID, VERSION)).thenThrow(DataNotFoundException.class);
    service.get(SURVEY_ID, VERSION);
    verify(activityDao, times(1)).getUndiscarded(SURVEY_ID, VERSION);
  }

  @Test
  public void updateCheckerActivityMethod_should_invoke_updateChekerActivity_of_ActivityDao() throws DataNotFoundException {
    mockStatic(CheckerUpdatedDTO.class);
    when(CheckerUpdatedDTO.deserialize(checkerUpdated)).thenReturn(checkerUpdatedDTO);
    service.updateCheckerActivity(checkerUpdated);
    verify(activityDao, times(1)).updateCheckerActivity(checkerUpdatedDTO);
  }

  @Test
  public void method_getActivity_should_call_ActivityDao_getLastFinalizedActivity() throws DataNotFoundException {
    service.getActivity(ACRONYM, VERSION, CATEGORY_NAME, RECRUIMENT_NUMBER);
    verify(activityDao, times(1)).getLastFinalizedActivity(ACRONYM, VERSION, CATEGORY_NAME, RECRUIMENT_NUMBER);
  }

  @Test(expected = DataNotFoundException.class)
  public void method_getActivity_should_throw_HttpResponseException() throws DataNotFoundException {
    doThrow(new DataNotFoundException(new Exception("method_RegisterProject_should_captured_DataNotFoundException"))).when(activityDao).getLastFinalizedActivity(ACRONYM, VERSION, CATEGORY_NAME,
      RECRUIMENT_NUMBER);
    service.getActivity(ACRONYM, VERSION, CATEGORY_NAME, RECRUIMENT_NUMBER);
  }

  @Test
  public void getActivityProgressExtraction_method_should_call_getActivityProgressExtraction_of_service() throws DataNotFoundException {
    service.getActivityProgressExtraction(CENTER);
    verify(activityProgressExtractionDao, times(1)).getActivityProgressExtraction(CENTER);
  }
}
