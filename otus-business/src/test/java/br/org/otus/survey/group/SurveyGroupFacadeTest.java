package br.org.otus.survey.group;

import br.org.otus.response.exception.HttpResponseException;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.service.surveyGroup.SurveyGroupService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class SurveyGroupFacadeTest {
    private static final String EXPECTED_ID = "5c7400d2d767afded0d84dcf";
    private static final String SURVEY_GROUP_NAME = "CI";
    private static final String USER_EMAIL = "otus@otus.com";
    private static final String OLD_NAME = "CI";
    private static final String NEW_NAME = "BA";

    @InjectMocks
    private SurveyGroupFacade surveyGroupFacade;
    @Mock
    private SurveyGroupService surveyGroupService;

    private SurveyGroup surveyGroup;
    private List<SurveyGroup> surveyGroups;
    private String surveyGroupJson;
    private ObjectId surveyGroupID;
    private ValidationException validationException;
    private DataNotFoundException dataNotFoundException;

    @Before
    public void setUp() throws Exception {
        surveyGroup = new SurveyGroup();
        surveyGroup.setName("CI");
        surveyGroups = asList(surveyGroup);
        surveyGroupJson = SurveyGroup.serialize(surveyGroup);
        surveyGroupID = new ObjectId("5c7400d2d767afded0d84dcf");
        validationException = PowerMockito.spy(new ValidationException());
        dataNotFoundException = PowerMockito.spy(new DataNotFoundException());
    }

    @Test
    public void getListOfSurveyGroupsMethod_should_invoke_getListOfSurveyGroups_of_surveyGroupService() {
        surveyGroupFacade.getListOfSurveyGroups();
        verify(surveyGroupService, times(1)).getListOfSurveyGroups();
    }

    @Test
    public void addNewGroupMethod_should_ID_in_case_of_persistence() throws ValidationException {
        when(surveyGroupService.addNewGroup(surveyGroupJson)).thenReturn(surveyGroupID);
        assertEquals(EXPECTED_ID, surveyGroupFacade.addNewGroup(surveyGroupJson));
    }

    @Test(expected = HttpResponseException.class)
    public void addNewGroupMethod_should_handle_ValidationException_for_json_invalid() throws ValidationException {
        when(surveyGroupService.addNewGroup(surveyGroupJson)).thenThrow(validationException);
        surveyGroupFacade.addNewGroup(surveyGroupJson);
    }

    @Test
    public void updateGroupSurveyAcronyms_should_invoke_updateGroupSurveyAcronyms_of_SurveyGroupService() throws ValidationException, DataNotFoundException {
        surveyGroupFacade.updateGroupSurveyAcronyms(surveyGroupJson);
        verify(surveyGroupService, times(1)).updateGroupSurveyAcronyms(surveyGroupJson);
    }

    @Test(expected = HttpResponseException.class)
    public void updateGroupSurveyAcronyms_should_handle_ValidationException_for_json_invalid() throws ValidationException, DataNotFoundException {
        when(surveyGroupService.updateGroupSurveyAcronyms(surveyGroupJson)).thenThrow(validationException);
        surveyGroupFacade.updateGroupSurveyAcronyms(surveyGroupJson);
    }

    @Test(expected = HttpResponseException.class)
    public void updateGroupSurveyAcronyms_should_handle_DataNotFoundException_for_json_invalid() throws ValidationException, DataNotFoundException {
        when(surveyGroupService.updateGroupSurveyAcronyms(surveyGroupJson)).thenThrow(dataNotFoundException);
        surveyGroupFacade.updateGroupSurveyAcronyms(surveyGroupJson);
    }

    @Test
    public void updateSurveyGroupName_should_invoke_updateSurveyGroupName_of_SurveyGroupService() throws ValidationException, DataNotFoundException {
        surveyGroupFacade.updateSurveyGroupName(OLD_NAME, NEW_NAME);
        verify(surveyGroupService, times(1)).updateSurveyGroupName(OLD_NAME, NEW_NAME);
    }

    @Test(expected = HttpResponseException.class)
    public void updateSurveyGroupName_should_handle_ValidationException_for_json_invalid() throws ValidationException, DataNotFoundException {
        when(surveyGroupService.updateSurveyGroupName(OLD_NAME, NEW_NAME)).thenThrow(validationException);
        surveyGroupFacade.updateSurveyGroupName(OLD_NAME, NEW_NAME);
    }

    @Test(expected = HttpResponseException.class)
    public void updateGroup_should_handle_DataNotFoundException_for_json_invalid() throws ValidationException, DataNotFoundException {
        when(surveyGroupService.updateSurveyGroupName(OLD_NAME, NEW_NAME)).thenThrow(dataNotFoundException);
        surveyGroupFacade.updateSurveyGroupName(OLD_NAME, NEW_NAME);
    }

    @Test
    public void deleteGroup_should_invoke_deleteGroup_of_SurveyGroupService() throws DataNotFoundException {
        surveyGroupFacade.deleteGroup(SURVEY_GROUP_NAME);
        verify(surveyGroupService, times(1)).deleteGroup(SURVEY_GROUP_NAME);
    }

    @Test(expected = HttpResponseException.class)
    public void deleteGroup_should_handle_DataNotFoundException_for_json_invalid() throws Exception {
        doThrow(dataNotFoundException).when(surveyGroupService,"deleteGroup", SURVEY_GROUP_NAME);
        surveyGroupFacade.deleteGroup(SURVEY_GROUP_NAME);
    }

    @Test
    public void getSurveyGroupsByUser_shold_return_list_of_SurveyGroups() {
        when(surveyGroupService.getSurveyGroupsByUser(USER_EMAIL)).thenReturn(surveyGroups);
        assertFalse(surveyGroupFacade.getSurveyGroupsByUser(USER_EMAIL).isEmpty());
    }
}