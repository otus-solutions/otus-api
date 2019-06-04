package br.org.otus.survey.activity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.org.otus.survey.activity.activityRevision.ActivityRevisionFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.survey.activity.api.ActivityFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AuthorizationHeaderReader.class)

public class ActivityResourceTest {
  private static final long RECRUIMENT_NUMBER = 3051442;
  private static final String ID_SURVEY_ACITIVITY = "591a40807b65e4045b9011e7";
  private static final String ID_ACITIVITY = "5c41c6b316da48006573a169";
  private static final String ACTIVITY_EXPECTED = "{\"data\":\"591a40807b65e4045b9011e7\"}";
  private static final String ACTIVITY_REVISION_EXPECTED = "{\"data\":true}";
  private static final String ACTIVITY_REVISION_JSON = "{\"activityID\" : \"5c41c6b316da48006573a169\",\"reviewDate\" : \"17/01/2019\"}";
  private static final String ACTIVITY_EXPECTED_BOOLEAN = "{\"data\":true}";
  private static final Integer VERSION = 1;
  private static final String ACRONYM = "TSTAC";
  private static final String userEmail = "otus@otus.com";
  private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String checkerUpdated = "{\"id\":\"5c0e5d41e69a69006430cb75\",\"activityStatus\":{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_OFFLINE\",\"date\":\"2018-12-10T12:33:29.007Z\",\"user\":{\"name\":\"Otus\",\"surname\":\"Solutions\",\"extraction\":true,\"extractionIps\":[\"999.99.999.99\"],\"phone\":\"21987654321\",\"fieldCenter\":{},\"email\":\"otus@gmail.com\",\"admin\":false,\"enable\":true,\"meta\":{\"revision\":0,\"created\":0,\"version\":0},\"$loki\":2}}}";


  @InjectMocks
  private ActivityResource activityResource;
  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private ParticipantFacade participantFacade;
  @Mock
  private ActivityService activityService;
  @Mock
  private ActivityFacade activityFacade;
  @Mock
  private ActivityRevisionFacade activityRevisionFacade;
  @Mock
  private SurveyForm surveyForm;

  private SurveyForm surveyFormDeserialize;
  private String jsonActivity = "activity";
  private SurveyActivity activityDeserialize;
  private List<SurveyActivity> listSurveyActivity;
  private List<ActivityRevision> listActivityRevision;
  private Participant participant;

  @Mock
  private HttpServletRequest request;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private SessionIdentifier sessionIdentifier;
  @Mock
  private AuthenticationData authenticationData;

  @Before
  public void setUp() {
    participant = new Participant((long) RECRUIMENT_NUMBER);
    when(participantFacade.getByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    jsonActivity = ActivitySimplifiedFactory.create().toString();
    activityDeserialize = SurveyActivity.deserialize(jsonActivity);
    listSurveyActivity = new ArrayList<SurveyActivity>();
    listSurveyActivity.add(SurveyActivity.deserialize(jsonActivity));
    surveyFormDeserialize = SurveyForm.deserialize(jsonActivity);
  }

  @Test
  public void method_getAll_should_return_entire_list_by_recruitment_number() throws Exception {
    when(request.getHeader(Mockito.anyString())).thenReturn(TOKEN);
    PowerMockito.mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.readToken(TOKEN)).thenReturn(TOKEN);
    when(securityContext.getSession(TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(userEmail);
    when(activityFacade.list(RECRUIMENT_NUMBER, userEmail)).thenReturn(listSurveyActivity);
    String listSurveyActivityExpected = new Response().buildSuccess(listSurveyActivity).toSurveyJson();
    assertEquals(listSurveyActivityExpected, activityResource.getAll(request, RECRUIMENT_NUMBER));
  }

  @Test
  public void method_createActivity_should_return_ObjectResponse() {
    when(activityFacade.deserialize(jsonActivity)).thenReturn(activityDeserialize);
    when(activityFacade.create(activityDeserialize)).thenReturn(ID_SURVEY_ACITIVITY);
    assertEquals(ACTIVITY_EXPECTED, activityResource.createActivity(RECRUIMENT_NUMBER, jsonActivity));
    verify(participantFacade).getByRecruitmentNumber(anyLong());
    verify(activityFacade).deserialize(anyString());
    verify(activityFacade).create(anyObject());
  }

  @Test
  public void method_getByID_should_return_ObjectResponse() throws DataNotFoundException {
    when(participantFacade.getByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    when(activityFacade.getByID(ID_SURVEY_ACITIVITY)).thenReturn(activityDeserialize);
    when(activityService.getByID(ID_SURVEY_ACITIVITY)).thenReturn(activityDeserialize);
    String responseExpected = new Response().buildSuccess(activityFacade.getByID(ID_SURVEY_ACITIVITY)).toSurveyJson();
    assertEquals(responseExpected, activityResource.getByID(RECRUIMENT_NUMBER, ID_SURVEY_ACITIVITY));
  }

  @Test
  public void method_update_should_return_update_ObjectResponse() {
    when(activityFacade.deserialize(jsonActivity)).thenReturn(activityDeserialize);
    when(activityFacade.updateActivity(activityFacade.deserialize(jsonActivity))).thenReturn(activityDeserialize);
    SurveyActivity deserializeActivityUpdate = activityFacade.updateActivity(activityFacade.deserialize(jsonActivity));
    SurveyActivity updatedActivity = activityFacade.updateActivity(deserializeActivityUpdate);
    String responseExpected = new Response().buildSuccess(updatedActivity).toSurveyJson();
    assertEquals(responseExpected, activityResource.update(RECRUIMENT_NUMBER, ID_SURVEY_ACITIVITY, jsonActivity));
    verify(participantFacade).getByRecruitmentNumber(anyLong());
  }

  @Test
  public void updateCheckerActivityMethod_should_return_responseBooleanData() {
    when(activityFacade.updateCheckerActivity(checkerUpdated)).thenReturn(Boolean.TRUE);
    assertEquals(ACTIVITY_EXPECTED_BOOLEAN, activityResource.updateCheckerActivity(RECRUIMENT_NUMBER, checkerUpdated));
  }

  @Test
  public void method_createActivityRevision_should_return_ObjectResponse() {
    when(request.getHeader(Mockito.anyString())).thenReturn(TOKEN);
    PowerMockito.mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.readToken(TOKEN)).thenReturn(TOKEN);
    when(securityContext.getSession(TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(userEmail);
    activityRevisionFacade.create(ACTIVITY_REVISION_JSON, userEmail);
    assertEquals(ACTIVITY_REVISION_EXPECTED, activityResource.createActivityRevision(request,ACTIVITY_REVISION_JSON));
    verify(activityRevisionFacade,times (2)).create(ACTIVITY_REVISION_JSON, userEmail);
  }

  @Test
  public void method_list_should_return_entire_getActivityRevisions_by_recruitment_number(){
    when(activityRevisionFacade.getActivityRevisions(ID_ACITIVITY)).thenReturn(listActivityRevision);
    String listSurveyActivityExpected = new Response().buildSuccess(listActivityRevision).toSurveyJson();
    assertEquals(listSurveyActivityExpected, activityResource.getActivityRevisions(request,ID_ACITIVITY));
  }

  @Test
  public void method_find_should_return_entire_getActivityFull(){
    when(activityFacade.getSurveyTemplate(ACRONYM,VERSION)).thenReturn(surveyFormDeserialize);
    String listSurveyActivityExpected = new Response().buildSuccess(surveyFormDeserialize).toSurveyJson();
    assertEquals(listSurveyActivityExpected, activityResource.getSurveyTemplate(ACRONYM,VERSION));
  }

}
