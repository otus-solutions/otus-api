package br.org.otus.survey.group;

import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({AuthorizationHeaderReader.class})
public class SurveyGroupResourceTest {
    private static final String EXPECTED_RESPONSE_LIST = "{\"data\":[{\"_id\":null,\"objectType\":\"SurveyGroup\",\"name\":\"CI\",\"surveyAcronyms\":null}]}";
    private static final String EXPECTED_RESPONSE_ID = "{\"data\":\"5c7400d2d767afded0d84dcf)\"}";
    private static final String EXPECTED_RESPONSE_UPDATE = "{\"data\":\" modifiedCount: 1\"}";
    private static final String EXPECTED_RESPONSE_DELETE = "{\"data\":true}";


    private static final String ID = "5c7400d2d767afded0d84dcf)";
    private static final String MODIFIELD_COUNT = "1";
    private static final String SURVEY_GROUP_NAME = "CI";
    private static final String USER_MAIL = "otus@otus.com";
    private static final String TOKEN = "123456";

    @InjectMocks
    private SurveyGroupResource surveyGroupResource;
    @Mock
    private SurveyGroupFacade surveyGroupFacade;
    @Mock
    private HttpServletRequest request;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private SessionIdentifier session;
    @Mock
    private AuthenticationData authenticationData;

    private SurveyGroup surveyGroup;
    private List<SurveyGroup> surveyGroups;
    private String surveyGroupJson;
    private String result;
    private String token = "123456";

    @Before
    public void setUp() throws Exception {
        surveyGroup = new SurveyGroup();
        surveyGroup.setName("CI");
        surveyGroups = asList(surveyGroup);
        surveyGroupJson = SurveyGroup.serialize(surveyGroup);
    }

    @Test
    public void getListOfSurveyGroups() {
        when(surveyGroupFacade.getListOfSurveyGroups()).thenReturn(surveyGroups);
        assertEquals(EXPECTED_RESPONSE_LIST, surveyGroupResource.getListOfSurveyGroups());
    }

    @Test
    public void addNewGroup() {
        when(surveyGroupFacade.addNewGroup(surveyGroupJson)).thenReturn(ID);
        assertEquals(EXPECTED_RESPONSE_ID, surveyGroupResource.addNewGroup(surveyGroupJson));
    }

    @Test
    public void updateGroup() {
        when(surveyGroupFacade.updateGroup(surveyGroupJson)).thenReturn(MODIFIELD_COUNT);
        assertEquals(EXPECTED_RESPONSE_UPDATE,surveyGroupResource.updateGroup(surveyGroupJson));
    }

    @Test
    public void deleteGroup() {
        assertEquals(EXPECTED_RESPONSE_DELETE, surveyGroupResource.deleteGroup(SURVEY_GROUP_NAME));
        Mockito.verify(surveyGroupFacade, Mockito.times(1)).deleteGroup(SURVEY_GROUP_NAME);
    }

    @Test
    public void getSurveyGroupsByUser() {
        mockStatic(AuthorizationHeaderReader.class);
        when(request.getHeader(Mockito.any())).thenReturn(TOKEN);
        when(AuthorizationHeaderReader.readToken(Mockito.any())).thenReturn(TOKEN);
        when(securityContext.getSession(TOKEN)).thenReturn(session);
        when(session.getAuthenticationData()).thenReturn(authenticationData);
        when(authenticationData.getUserEmail()).thenReturn(USER_MAIL);
        when(surveyGroupFacade.getSurveyGroupsByUser(USER_MAIL)).thenReturn(surveyGroups);

        assertEquals(EXPECTED_RESPONSE_LIST,surveyGroupResource.getSurveyGroupsByUser(request));
    }

}