package org.ccem.otus.service.surveyGroup;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.model.survey.group.dto.SurveyGroupNameDto;
import org.ccem.otus.persistence.SurveyGroupDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.reflect.Whitebox.invokeMethod;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SurveyGroupServiceBean.class, SurveyGroup.class})
public class SurveyGroupServiceBeanTest {

    private static final int EXPECTED_SIZE = 1;
    private static final String USER_EMAIL = "otus@otus.com";
    private static final String SURVEY_GROUP_ID = "5c7400d2d767afded0d84dcf";
    private static final ObjectId EXPECTED_ID = new ObjectId(SURVEY_GROUP_ID);
    private static final String EMPTY_JSON = "";
    private static final String SURVEY_GROUP_NAME = "RECRUTAMENTO";
    private static final String EXPECTED_CHANGE = "1";
    private static final String NEW_SURVEY_GROUP_NAME = "APÓS CHAMADAS";

    @InjectMocks
    private SurveyGroupServiceBean surveyGroupServiceBean = PowerMockito.spy(new SurveyGroupServiceBean());
    @Mock
    private SurveyGroupDao surveyGroupDao;

    private SurveyGroupNameDto surveyGroupNameDto;
    @Mock
    private DeleteResult result;
    private SurveyGroup surveyGroup;
    private List<SurveyGroup> surveyGroups;
    private String surveyGroupJson;

    @Before
    public void setUp() throws Exception {
        surveyGroup = new SurveyGroup();
        surveyGroup.setName("RECRUTAMENTO");
        surveyGroups = asList(surveyGroup);
        surveyGroupJson = SurveyGroup.serialize(surveyGroup);
        surveyGroupNameDto = new SurveyGroupNameDto();
    }

    @Test
    public void getListOfSurveyGroupsMethod_should_return_list_SurveyGroups() {
        when(surveyGroupDao.getListOfSurveyGroups()).thenReturn(surveyGroups);
        assertEquals(EXPECTED_SIZE, surveyGroupServiceBean.getListOfSurveyGroups().size());
    }

    @Test
    public void addNewGroup_should_return_ID_in_case_sucess_persist() throws Exception {
        mockStatic(SurveyGroup.class);
        when(SurveyGroup.deserialize(surveyGroupJson)).thenReturn(surveyGroup);
        when(surveyGroupDao.persist(surveyGroup)).thenReturn(new ObjectId(SURVEY_GROUP_ID));
        assertEquals(EXPECTED_ID, surveyGroupServiceBean.addNewSurveyGroup(surveyGroupJson));
        verifyPrivate(surveyGroupServiceBean, times(1)).invoke("verifySurveyGroupNameValid", surveyGroup);
        verifyPrivate(surveyGroupServiceBean, times(1)).invoke("verifySurveyGroupNameConflits", surveyGroup.getName());
    }

    @Test(expected = ValidationException.class)
    public void addNewGroupMethod_with_paramenter_empty_should_throws_ValidationException() throws Exception {
        surveyGroupServiceBean.addNewSurveyGroup(EMPTY_JSON);
    }

    @Test(expected = ValidationException.class)
    public void addNewGroupMethod_with_surveyGroupName_invalid_should_throws_ValidationException() throws Exception {
        mockStatic(SurveyGroup.class);
        when(SurveyGroup.deserialize(surveyGroupJson)).thenReturn(surveyGroup);
        surveyGroup.setName("");
        surveyGroupServiceBean.addNewSurveyGroup(surveyGroupJson);
    }

    @Test(expected = ValidationException.class)
    public void addNewGroupMethod_with_surveyGroupName_existence_should_throws_ValidationException() throws Exception {
        invokeMethod(surveyGroupServiceBean, "verifySurveyGroupNameConflits", SURVEY_GROUP_NAME);
        doThrow(new ValidationException()).when(surveyGroupDao, "findSurveyGroupNameConflits", SURVEY_GROUP_NAME);
        surveyGroupServiceBean.addNewSurveyGroup(surveyGroupJson);
    }

    @Test
    public void updateGroupSurveyAcronymsMethod_should_returns_value_in_case_sucess_update_of_acronyms() throws DataNotFoundException {
        mockStatic(SurveyGroup.class);
        when(SurveyGroup.deserialize(surveyGroupJson)).thenReturn(surveyGroup);
        when(surveyGroupDao.updateSurveyGroupAcronyms(surveyGroup)).thenReturn(EXPECTED_CHANGE);
        assertEquals(EXPECTED_CHANGE, surveyGroupServiceBean.updateSurveyGroupAcronyms(surveyGroupJson));
    }

    @Test(expected = DataNotFoundException.class)
    public void updateGroupSurveyAcronymsMethod_should_trows_exception_in_case_surveyGroup_inexistence() throws Exception {
        invokeMethod(surveyGroupServiceBean, "verifySurveyGroupNameExists", surveyGroupJson);
        doThrow(new DataNotFoundException()).when(surveyGroupDao, "findSurveyGroupByName", SURVEY_GROUP_NAME);
        surveyGroupServiceBean.updateSurveyGroupAcronyms(surveyGroupJson);
    }

    @Test
    public void updateSurveyGroupNameMethod_should_returns_value_in_case_sucess_update_of_surveyGroupName() throws Exception {
        surveyGroupNameDto.setSurveyGroupName(SURVEY_GROUP_NAME);
        surveyGroupNameDto.setNewSurveyGroupName(NEW_SURVEY_GROUP_NAME);
        when(surveyGroupDao.updateGroupName(SURVEY_GROUP_NAME, NEW_SURVEY_GROUP_NAME)).thenReturn(EXPECTED_CHANGE);
        assertEquals(EXPECTED_CHANGE, surveyGroupServiceBean.updateSurveyGroupName(surveyGroupNameDto));
        verifyPrivate(surveyGroupServiceBean, times(1)).invoke("verifySurveyGroupNameExists", surveyGroupNameDto.getSurveyGroupName());
        verifyPrivate(surveyGroupServiceBean, times(1)).invoke("verifyNewSurveyGroupName", surveyGroupNameDto.getNewSurveyGroupName());
        verifyPrivate(surveyGroupServiceBean, times(1)).invoke("verifySurveyGroupNameConflits", surveyGroupNameDto.getNewSurveyGroupName());
    }

    @Test(expected = DataNotFoundException.class)
    public void updateSurveyGroupNameMethod_should_trows_exception_in_case_surveyGroup_inexistence() throws Exception {
        surveyGroupNameDto.setSurveyGroupName(SURVEY_GROUP_NAME);
        surveyGroupNameDto.setNewSurveyGroupName(NEW_SURVEY_GROUP_NAME);
        invokeMethod(surveyGroupServiceBean, "verifySurveyGroupNameExists", SURVEY_GROUP_NAME);
        doThrow(new DataNotFoundException()).when(surveyGroupDao, "findSurveyGroupByName", SURVEY_GROUP_NAME);
        surveyGroupServiceBean.updateSurveyGroupName(surveyGroupNameDto);
    }

    @Test(expected = ValidationException.class)
    public void updateSurveyGroupNameMethod_should_trows_exception_in_case_surveyGroupName_invalid() throws Exception {
        surveyGroupServiceBean.updateSurveyGroupName(surveyGroupNameDto);
    }

    @Test(expected = ValidationException.class)
    public void updateSurveyGroupNameMethod_should_trows_exception_in_case_existing_surveyGroupName() throws Exception {
        surveyGroupNameDto.setSurveyGroupName(SURVEY_GROUP_NAME);
        surveyGroupNameDto.setNewSurveyGroupName(NEW_SURVEY_GROUP_NAME);
        invokeMethod(surveyGroupServiceBean, "verifySurveyGroupNameConflits", NEW_SURVEY_GROUP_NAME);
        doThrow(new ValidationException()).when(surveyGroupDao, "findSurveyGroupNameConflits", NEW_SURVEY_GROUP_NAME);
        surveyGroupServiceBean.updateSurveyGroupName(surveyGroupNameDto);
    }

    @Test(expected = DataNotFoundException.class)
    public void deleteGroupMethod_should_simulate_fail_delete_of_survey_group() throws DataNotFoundException {
        surveyGroupNameDto.setSurveyGroupName(SURVEY_GROUP_NAME);
        when(surveyGroupDao.deleteSurveyGroup(SURVEY_GROUP_NAME)).thenReturn(result);
        when(result.getDeletedCount()).thenReturn(0L);
        surveyGroupServiceBean.deleteSurveyGroup(surveyGroupNameDto);
    }

    @Test
    public void deleteGroupMethod_should_simulate_success_delete_of_survey_group() throws DataNotFoundException {
        surveyGroupNameDto.setSurveyGroupName(SURVEY_GROUP_NAME);
        when(surveyGroupDao.deleteSurveyGroup(SURVEY_GROUP_NAME)).thenReturn(result);
        when(result.getDeletedCount()).thenReturn(1L);
        surveyGroupServiceBean.deleteSurveyGroup(surveyGroupNameDto);
    }

    @Test
    public void getSurveyGroupsByUser() {
        when(surveyGroupDao.getSurveyGroupsByUser(USER_EMAIL)).thenReturn(surveyGroups);
        assertEquals(EXPECTED_SIZE, surveyGroupServiceBean.getSurveyGroupsByUser(USER_EMAIL).size());
    }
}