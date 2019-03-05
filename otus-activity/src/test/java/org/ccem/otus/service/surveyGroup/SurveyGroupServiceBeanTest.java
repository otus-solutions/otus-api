package org.ccem.otus.service.surveyGroup;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.persistence.SurveyGroupDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SurveyGroupServiceBean.class, SurveyGroup.class})
public class SurveyGroupServiceBeanTest {

    private static final int EXPECTED_SIZE = 1;
    private static final String USER_EMAIL = "otus@otus.com";
    private static final String SURVEY_GROUP_ID = "5c7400d2d767afded0d84dcf";
    private static final ObjectId EXPECTED_ID = new ObjectId(SURVEY_GROUP_ID);
    private static final String EMPTY_JSON = "";
    private static final String SURVEY_GROUP_NAME = "CI";

    @InjectMocks
    private SurveyGroupServiceBean surveyGroupServiceBean = PowerMockito.spy(new SurveyGroupServiceBean());
    @Mock
    private SurveyGroupDao surveyGroupDao;
    private SurveyGroup surveyGroup;
    private List<SurveyGroup> surveyGroups;
    private String surveyGroupJson;

    @Before
    public void setUp() throws Exception {
        surveyGroup = new SurveyGroup();
        surveyGroup.setName("CI");
        surveyGroups = asList(surveyGroup);
        surveyGroupJson = SurveyGroup.serialize(surveyGroup);
    }

    @Test
    public void getListOfSurveyGroupsMethod_should_return_list_SurveyGroups() {
        when(surveyGroupDao.getListOfSurveyGroups()).thenReturn(surveyGroups);
        assertEquals(EXPECTED_SIZE, surveyGroupServiceBean.getListOfSurveyGroups().size());
    }

    @Test
    public void addNewGroup_should_return_id_in_case_sucess_persist() throws Exception {
        mockStatic(SurveyGroup.class);
        when(SurveyGroup.deserialize(surveyGroupJson)).thenReturn(surveyGroup);
        when(surveyGroupDao.persist(surveyGroup)).thenReturn(new ObjectId(SURVEY_GROUP_ID));
        assertEquals(EXPECTED_ID, surveyGroupServiceBean.addNewGroup(surveyGroupJson));
        verifyPrivate(surveyGroupServiceBean, times(1)).invoke("verifySurveyGroupNameValid", surveyGroup);
        verifyPrivate(surveyGroupServiceBean, times(1)).invoke("verifySurveyGroupNameConflits", surveyGroup.getName());
    }

    @Test(expected = ValidationException.class)
    public void addNewGroupMethod_with_paramenter_empty_should_throws_ValidationException() throws Exception {
        surveyGroupServiceBean.addNewGroup(EMPTY_JSON);
    }

    @Test(expected = ValidationException.class)
    public void addNewGroupMethod_with_surveyGroupName_invalid_should_throws_ValidationException() throws Exception {
        mockStatic(SurveyGroup.class);
        when(SurveyGroup.deserialize(surveyGroupJson)).thenReturn(surveyGroup);
        surveyGroup.setName("ci");
        surveyGroupServiceBean.addNewGroup(surveyGroupJson);
    }

    @Test(expected = ValidationException.class)
    public void addNewGroupMethod_with_surveyGroupName_existence_should_throws_ValidationException() throws Exception {
        Whitebox.invokeMethod(surveyGroupServiceBean, "verifySurveyGroupNameConflits", SURVEY_GROUP_NAME);
        PowerMockito.doThrow(new ValidationException()).when(surveyGroupDao, "findSurveyGroupNameConflits", SURVEY_GROUP_NAME);
        surveyGroupServiceBean.addNewGroup(surveyGroupJson);
    }

    @Test
    public void updateGroup() {
    }

    @Test
    public void deleteGroup() {
    }

    @Test
    public void getSurveyGroupsByUser() {
        when(surveyGroupDao.getSurveyGroupsByUser(USER_EMAIL)).thenReturn(surveyGroups);
        assertEquals(EXPECTED_SIZE, surveyGroupServiceBean.getSurveyGroupsByUser(USER_EMAIL).size());
    }
}