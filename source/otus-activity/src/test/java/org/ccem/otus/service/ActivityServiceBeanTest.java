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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.dto.CheckerUpdatedDTO;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.persistence.ActivityDao;
import org.ccem.otus.persistence.ActivityExtractionDao;
import org.ccem.otus.persistence.ActivityProgressExtractionDao;
import org.ccem.otus.persistence.OfflineActivityDao;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.identity.Identity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CheckerUpdatedDTO.class)
public class ActivityServiceBeanTest {

  private static final String ACTIVITY_ID = "58c83f502226685b94f8973a";
  private static final ObjectId ACTIVITY_OID = new ObjectId(ACTIVITY_ID);
  private static final long RECRUITMENT_NUMBER = 12345;
  private static final String SURVEY_ID = "123456789";
  private static final Integer VERSION = 1;
  private static final String USER_EMAIL = "otus@gmail.com";
  private static final String CHECKER_UPDATED = "{\"id\":\"5c0e5d41e69a69006430cb75\",\"activityStatus\":{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_OFFLINE\",\"date\":\"2018-12-10T12:33:29.007Z\",\"user\":{\"name\":\"Otus\",\"surname\":\"Solutions\",\"extraction\":true,\"extractionIps\":[\"999.99.999.99\"],\"phone\":\"21987654321\",\"fieldCenter\":{},\"email\":\"otus@gmail.com\",\"admin\":false,\"enable\":true,\"meta\":{\"revision\":0,\"created\":0,\"version\":0},\"$loki\":2}}}";
  private static final String ACRONYM = "ACTA";
  private static final String CATEGORY_NAME = "C0";
  private static final String CENTER = "RS";
  private static final String EMAIL= "email@email.com";
  private static final ObjectId STAGE_OID = new ObjectId("5f77920624439758ce4a43ab");


  @InjectMocks
  private ActivityServiceBean service;
  @Mock
  private ActivityDao activityDao;
  @Mock
  private ActivityAccessPermissionService activityAccessPermissionService;
  @Mock
  private ActivityProgressExtractionDao activityProgressExtractionDao;
  @Mock
  private ActivityExtractionDao activityExtractionDao;
  @Mock
  private OfflineActivityDao offlineActivityDao;
  @Mock
  private UserDao userDao;

  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private SurveyForm surveyForm;
  @Mock
  private SurveyTemplate surveyTemplate;
  @Mock
  private Identity identity;
  @Mock
  private CheckerUpdatedDTO checkerUpdatedDTO;


  private List<SurveyActivity> surveyActivities;
  private User user;

  @Before
  public void setup() {
    Whitebox.setInternalState(identity, "acronym", "TST");
    Whitebox.setInternalState(identity, "name", "TEST");
    Whitebox.setInternalState(surveyTemplate, "identity", identity);
    when(activityDao.persist(any())).thenReturn(ACTIVITY_OID);
    when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);
    when(surveyForm.getSurveyTemplate()).thenReturn(surveyTemplate);
    ArrayList<SurveyActivity> activities = new ArrayList<SurveyActivity>();
    activities.add(surveyActivity);
    when(activityDao.find(new ArrayList<>(), USER_EMAIL, RECRUITMENT_NUMBER)).thenReturn(activities);

    user = new User();
    user.set_id(new ObjectId());
  }

  @Test
  public void method_create_should_call_ActivityDao_persist_method_with_a_mySurveyActivity() {
    String resultId = service.create(surveyActivity);
    verify(activityDao, times(1)).persist(surveyActivity);
    verify(surveyForm, times(1)).setSurveyTemplate(null);
    assertEquals(ACTIVITY_ID, resultId);
  }

  @Test
  public void update_method_should_call_ActivityDao_update_method_with_a_mySurveyActivity() throws DataNotFoundException {
    service.update(surveyActivity);
    verify(activityDao, times(1)).update(surveyActivity);
    verify(surveyForm, times(1)).setSurveyTemplate(null);
  }

  @Test
  public void method_listByStageGroups_should_invoke_findByStageGroup_of_ActivityDao() {
    surveyActivities = service.list(RECRUITMENT_NUMBER, USER_EMAIL);
    assertTrue(surveyActivities.get(0) instanceof SurveyActivity);
    verify(activityDao, times(1)).find(new ArrayList<>(), USER_EMAIL, RECRUITMENT_NUMBER);
    verify(activityAccessPermissionService, times(1)).list();
  }

  @Test
  public void listMethod_should_invoke_find_of_ActivityDao_find() throws MemoryExcededException {
    final Map<ObjectId, String> STAGE_MAP = new HashMap<>();
    when(activityDao.findByStageGroup(new ArrayList<>(), USER_EMAIL, RECRUITMENT_NUMBER)).thenReturn(new ArrayList<>());
    service.listByStageGroups(RECRUITMENT_NUMBER, USER_EMAIL, STAGE_MAP);
    verify(activityDao, times(1)).findByStageGroup(new ArrayList<>(), USER_EMAIL, RECRUITMENT_NUMBER);
  }

  @Test
  public void method_getByID_should_call_ActivityDao_findByID() throws DataNotFoundException {
    service.getByID(SURVEY_ID);
    verify(activityDao, times(1)).findByID(SURVEY_ID);
  }

  @Test
  public void method_get_should_call_ActivityDao_getUndiscarded() throws DataNotFoundException, MemoryExcededException {
    service.get(SURVEY_ID, VERSION);
    verify(activityDao, times(1)).getUndiscarded(SURVEY_ID, VERSION);
  }

  @SuppressWarnings("unchecked")
  @Test(expected = DataNotFoundException.class)
  public void method_get_should_throw_DataNotFound_when_getUndiscarded_not_found() throws DataNotFoundException, MemoryExcededException {
    when(activityDao.getUndiscarded(SURVEY_ID, VERSION)).thenThrow(DataNotFoundException.class);
    service.get(SURVEY_ID, VERSION);
    verify(activityDao, times(1)).getUndiscarded(SURVEY_ID, VERSION);
  }

  @Test
  public void updateCheckerActivityMethod_should_invoke_updateChekerActivity_of_ActivityDao() throws DataNotFoundException {
    mockStatic(CheckerUpdatedDTO.class);
    when(CheckerUpdatedDTO.deserialize(CHECKER_UPDATED)).thenReturn(checkerUpdatedDTO);
    service.updateCheckerActivity(CHECKER_UPDATED);
    verify(activityDao, times(1)).updateCheckerActivity(checkerUpdatedDTO);
  }

  @Test
  public void method_getActivity_should_call_ActivityDao_getLastFinalizedActivity() throws DataNotFoundException {
    service.getActivity(ACRONYM, VERSION, CATEGORY_NAME, RECRUITMENT_NUMBER);
    verify(activityDao, times(1)).getLastFinalizedActivity(ACRONYM, VERSION, CATEGORY_NAME, RECRUITMENT_NUMBER);
  }

  @Test(expected = DataNotFoundException.class)
  public void method_getActivity_should_throw_HttpResponseException() throws DataNotFoundException {
    doThrow(new DataNotFoundException(new Exception("method_RegisterProject_should_captured_DataNotFoundException"))).when(activityDao).getLastFinalizedActivity(ACRONYM, VERSION, CATEGORY_NAME,
      RECRUITMENT_NUMBER);
    service.getActivity(ACRONYM, VERSION, CATEGORY_NAME, RECRUITMENT_NUMBER);
  }

  @Test
  public void getActivityProgressExtraction_method_should_call_getActivityProgressExtraction_of_service() throws DataNotFoundException {
    service.getActivityProgressExtraction(CENTER);
    verify(activityProgressExtractionDao, times(1)).getActivityProgressExtraction(CENTER);
  }

  @Test
  public void getExtraction_method_should_call_getExtraction_of_service() throws DataNotFoundException, MemoryExcededException {
    service.getExtraction(ACRONYM, VERSION);
    verify(activityDao, times(1)).getExtraction(ACRONYM, VERSION);
  }

  @Test
  public void getParticipantFieldCenterByActivity_method_should_call_getParticipantFieldCenter_of_service() throws DataNotFoundException {
    service.getParticipantFieldCenterByActivity(ACRONYM, VERSION);
    verify(activityExtractionDao, times(1)).getParticipantFieldCenter(ACRONYM, VERSION);
  }

  @Test
  public void save_method_should_invoke_persist_from_activityDao() throws DataNotFoundException {
    OfflineActivityCollection offlineActivityCollection = new OfflineActivityCollection();
    when(userDao.fetchByEmail(USER_EMAIL)).thenReturn(user);
    service.save(USER_EMAIL, offlineActivityCollection);
    verify(offlineActivityDao, times(1)).persist(offlineActivityCollection);
  }

  @Test
  public void fetchOfflineActivityCollections_method_should_invoke_fetchByUserId_from_activityDao() throws DataNotFoundException {
    when(userDao.fetchByEmail(USER_EMAIL)).thenReturn(user);
    service.fetchOfflineActivityCollections(USER_EMAIL);
    verify(offlineActivityDao, times(1)).fetchByUserId(user.get_id());
  }

  @Test
  public void fetchOfflineActivityCollection_method_should_invoke_fetchCollection_from_activityDao() throws DataNotFoundException {
    String collectionId = "";
    service.fetchOfflineActivityCollection(collectionId);
    verify(offlineActivityDao, times(1)).fetchCollection(collectionId);
  }

  @Test
  public void deactivateOfflineActivityCollection_method_should_invoke_deactivateOfflineActivityCollection_from_activityDao() throws DataNotFoundException {
    String collectionId = "";
    List<ObjectId> createdActivityIds = new ArrayList<>();
    service.deactivateOfflineActivityCollection(collectionId, createdActivityIds);
    verify(offlineActivityDao, times(1)).deactivateOfflineActivityCollection(collectionId, createdActivityIds);
  }

  @Test
  public void updateParticipantEmail_method_should_call_updateEmailByParticipant_of_dao() {
    service.updateParticipantEmail(RECRUITMENT_NUMBER, EMAIL);
    verify(activityDao, times(1)).updateParticipantEmail(RECRUITMENT_NUMBER, EMAIL);
  }

  @Test
  public void removeStageFromActivities_method_should_call_removeStageFromActivities_of_dao() {
    service.removeStageFromActivities(STAGE_OID);
    verify(activityDao, times(1)).removeStageFromActivities(STAGE_OID);
  }

  @Test
  public void discardByID_method_should_invoke_ActivityDao_discardByID() throws DataNotFoundException {
    service.discardByID(ACTIVITY_OID);
    verify(activityDao, times(1)).discardByID(ACTIVITY_OID);
  }

}
