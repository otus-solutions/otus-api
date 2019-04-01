package br.org.otus.survey.group;

import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.model.survey.group.dto.SurveyGroupNameDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
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
    @Mock
    private SurveyGroupNameDto surveyGroupNameDto;

    private SurveyGroup surveyGroup;
    private List<SurveyGroup> surveyGroups;
    private String surveyGroupJson;

    @Before
    public void setUp() {
        surveyGroup = new SurveyGroup();
        surveyGroup.setName("CI");
        surveyGroups = asList(surveyGroup);
        surveyGroupJson = SurveyGroup.serialize(surveyGroup);
    }

    @Test
    public void getListOfSurveyGroupsMethod_should_return_list_of_SurveyGroups() {
        when(surveyGroupFacade.getListOfSurveyGroups()).thenReturn(surveyGroups);
        assertEquals(EXPECTED_RESPONSE_LIST, surveyGroupResource.getListOfSurveyGroups());
    }

    @Test
    public void addNewGroupMethod_should_return_ID_of_surveyGroup_created() {
        when(surveyGroupFacade.addNewSurveyGroup(surveyGroupJson)).thenReturn(ID);
        assertEquals(EXPECTED_RESPONSE_ID, surveyGroupResource.addNewSurveyGroup(surveyGroupJson));
    }

    @Test
    public void updateSurveyGroupAcronymsMethod_should_return_signaling_with_change_value() {
        when(surveyGroupFacade.updateSurveyGroupAcronyms(surveyGroupJson)).thenReturn(MODIFIELD_COUNT);
        assertEquals(EXPECTED_RESPONSE_UPDATE, surveyGroupResource.updateSurveyGroupAcronyms(surveyGroupJson));
    }

    @Test
    public void updateSurveyGroupNameMethod_should_return_signaling_with_change_value() {
        when(surveyGroupFacade.updateSurveyGroupName(surveyGroupNameDto)).thenReturn(MODIFIELD_COUNT);
        assertEquals(EXPECTED_RESPONSE_UPDATE, surveyGroupResource.updateSurveyGroupName(surveyGroupNameDto));
    }

    @Test
    public void deleteGroupMethod_should_deleteGroup_by_surveyGroupFacade() {
        assertEquals(EXPECTED_RESPONSE_DELETE, surveyGroupResource.deleteSurveyGroup(surveyGroupNameDto));
        verify(surveyGroupFacade, Mockito.times(1)).deleteSurveyGroup(surveyGroupNameDto);
    }

    @Test
    public void getSurveyGroupsByUserMethod_should_return_list_of_SurverGroups() {
        mockStatic(AuthorizationHeaderReader.class);
        when(request.getHeader(Mockito.any())).thenReturn(TOKEN);
        when(AuthorizationHeaderReader.readToken(Mockito.any())).thenReturn(TOKEN);
        when(securityContext.getSession(TOKEN)).thenReturn(session);
        when(session.getAuthenticationData()).thenReturn(authenticationData);
        when(authenticationData.getUserEmail()).thenReturn(USER_MAIL);
        when(surveyGroupFacade.getSurveyGroupsByUser(USER_MAIL)).thenReturn(surveyGroups);
        assertEquals(EXPECTED_RESPONSE_LIST, surveyGroupResource.getSurveyGroupsByUser(request));
    }
}