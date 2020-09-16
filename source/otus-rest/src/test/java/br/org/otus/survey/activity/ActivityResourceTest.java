package br.org.otus.survey.activity;

import br.org.otus.rest.Response;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ActivityResourceTest {
  private static final String ACTIVITY_ID = "5f5fade308a0fc339325a8c8";

  @InjectMocks
  private ActivityResource activityResource;
  @Mock
  private ActivityFacade activityFacade;
  @Mock
  private HttpServletRequest request;

  private SurveyActivity autofillSurveyActivity;

  @Before
  public void setUp() throws Exception {
    autofillSurveyActivity = SurveyActivity.deserialize("{\"objectType\":\"Activity\",\"_id\":\"5f5f79927a37f42cb997efe4\",\"surveyForm\":{\"_id\":null,\"sender\":\"diogo.rosas.ferreira@gmail.com\",\"sendingDate\":\"2017-05-16T17:21:35.932Z\",\"objectType\":\"SurveyForm\",\"surveyFormType\":\"FORM_INTERVIEW\",\"surveyTemplate\":null,\"name\":\"TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO\",\"acronym\":\"TCLEC\",\"version\":1,\"isDiscarded\":false,\"requiredExternalID\":false},\"mode\":\"AUTOFILL\",\"category\":{\"name\":\"C0\",\"objectType\":\"ActivityCategory\",\"label\":\"Normal\",\"disabled\":false,\"isDefault\":true},\"participantData\":{\"_id\":\"5ea343bdb174c405c9bba6cc\",\"recruitmentNumber\":555555,\"name\":\"Fulano Detal Bezerra Pereira Menezes Rodrigues Gomes\",\"sex\":\"M\",\"birthdate\":{\"objectType\":\"ImmutableDate\",\"value\":\"1949-04-22 00:00:00.000\"},\"fieldCenter\":{\"_id\":\"587d366a7b65e477dc410ab9\",\"name\":\"Rio Grande do Sul\",\"code\":5,\"acronym\":\"RS\",\"country\":null,\"state\":null,\"address\":null,\"complement\":null,\"zip\":null,\"phone\":null,\"backgroundColor\":\"rgba(75, 192, 192, 0.2)\",\"borderColor\":\"rgba(75, 192, 192, 1)\",\"locationPoint\":null},\"late\":false,\"email\":\"fdrtec@gmail.com\",\"password\":null,\"tokenList\":null,\"registeredBy\":null,\"identified\":true},\"interviews\":[{\"objectType\":\"Interview\",\"date\":\"2020-09-11T13:34:03.801Z\",\"interviewer\":{\"objectType\":\"Interviewer\",\"name\":null,\"email\":null}}],\"fillContainer\":{\"fillingList\":[{\"objectType\":\"QuestionFill\",\"questionID\":\"TCLEC1\",\"customID\":null,\"answer\":{\"value\":\"2\",\"objectType\":\"AnswerFill\",\"type\":\"SingleSelectionQuestion\"},\"forceAnswer\":false,\"metadata\":{\"objectType\":\"MetadataFill\",\"value\":null},\"comment\":\"\"},{\"objectType\":\"QuestionFill\",\"questionID\":\"TCLEC2\",\"customID\":null,\"answer\":{\"value\":\"2\",\"objectType\":\"AnswerFill\",\"type\":\"SingleSelectionQuestion\"},\"forceAnswer\":false,\"metadata\":{\"objectType\":\"MetadataFill\",\"value\":null},\"comment\":\"\"},{\"objectType\":\"QuestionFill\",\"questionID\":\"TCLEC3\",\"customID\":null,\"answer\":{\"value\":null,\"objectType\":\"AnswerFill\",\"type\":\"FileUploadQuestion\"},\"forceAnswer\":false,\"metadata\":{\"objectType\":\"MetadataFill\",\"value\":\"2\"},\"comment\":\"\"}]},\"statusHistory\":[{\"objectType\":\"ActivityStatus\",\"name\":\"CREATED\",\"date\":\"2020-09-11T13:26:21.980Z\",\"user\":{\"name\":\"Fabiano\",\"surname\":\"Dias Ramires\",\"phone\":\"51998577574\",\"email\":\"fdrtec@gmail.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_ONLINE\",\"date\":\"2020-09-11T13:33:18.772Z\",\"user\":{\"name\":\"Fabiano\",\"surname\":\"Dias Ramires\",\"phone\":\"51998577574\",\"email\":\"fdrtec@gmail.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"FINALIZED\",\"date\":\"2020-09-11T13:34:03.800Z\",\"user\":{\"name\":\"Fabiano\",\"surname\":\"Dias Ramires\",\"phone\":\"51998577574\",\"email\":\"fdrtec@gmail.com\"}}],\"isDiscarded\":false,\"navigationTracker\":{\"objectType\":\"NavigationTracker\",\"items\":[{\"objectType\":\"NavigationTrackingItem\",\"id\":\"TCLEC1\",\"state\":\"ANSWERED\",\"previous\":null,\"inputs\":[],\"outputs\":[\"TCLEC2\"]},{\"objectType\":\"NavigationTrackingItem\",\"id\":\"TCLEC2\",\"state\":\"ANSWERED\",\"previous\":\"TCLEC1\",\"inputs\":[\"TCLEC1\"],\"outputs\":[\"TCLEC3\"]},{\"objectType\":\"NavigationTrackingItem\",\"id\":\"TCLEC3\",\"state\":\"ANSWERED\",\"previous\":\"TCLEC2\",\"inputs\":[\"TCLEC2\"],\"outputs\":[\"TCLEC4\"]},{\"objectType\":\"NavigationTrackingItem\",\"id\":\"TCLEC4\",\"state\":\"VISITED\",\"previous\":\"TCLEC3\",\"inputs\":[\"TCLEC3\"],\"outputs\":[]}],\"lastVisitedIndex\":4},\"externalID\":null}");
  }

  @Test
  public void getByID_method_should_get_activity_by_id() throws Exception {
    when(activityFacade.getByID(ACTIVITY_ID)).thenReturn(autofillSurveyActivity);
    String responseExpected = new Response().buildSuccess(activityFacade.getByID(ACTIVITY_ID)).toSurveyJson();
    assertEquals(responseExpected, activityResource.getByID(ACTIVITY_ID));
  }

  @Test
  public void update() throws Exception {
    when(activityFacade.updateActivity(any(), anyString())).thenReturn(autofillSurveyActivity);
    String responseExpected = new Response().buildSuccess(activityFacade.updateActivity(any(), anyString())).toSurveyJson();
    assertEquals(responseExpected, activityResource.update(request, autofillSurveyActivity.toString()));
  }
  
}