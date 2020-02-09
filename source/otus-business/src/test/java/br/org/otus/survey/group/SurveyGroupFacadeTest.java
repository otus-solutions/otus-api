package br.org.otus.survey.group;

import br.org.otus.response.exception.HttpResponseException;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.model.survey.group.dto.SurveyGroupNameDto;
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
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class SurveyGroupFacadeTest {
  private static final String EXPECTED_ID = "5c7400d2d767afded0d84dcf";
  private static final String USER_EMAIL = "otus@otus.com";

  @InjectMocks
  private SurveyGroupFacade surveyGroupFacade;
  @Mock
  private SurveyGroupService surveyGroupService;
  @Mock
  private SurveyGroupNameDto surveyGroupNameDto;

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
    when(surveyGroupService.addNewSurveyGroup(surveyGroupJson)).thenReturn(surveyGroupID);
    assertEquals(EXPECTED_ID, surveyGroupFacade.addNewSurveyGroup(surveyGroupJson));
  }

  @Test(expected = HttpResponseException.class)
  public void addNewGroupMethod_should_handle_ValidationException_for_json_invalid() throws ValidationException {
    when(surveyGroupService.addNewSurveyGroup(surveyGroupJson)).thenThrow(validationException);
    surveyGroupFacade.addNewSurveyGroup(surveyGroupJson);
  }

  @Test
  public void updateGroupSurveyAcronyms_should_invoke_updateGroupSurveyAcronyms_of_SurveyGroupService() throws ValidationException, DataNotFoundException {
    surveyGroupFacade.updateSurveyGroupAcronyms(surveyGroupJson);
    verify(surveyGroupService, times(1)).updateSurveyGroupAcronyms(surveyGroupJson);
  }

  @Test(expected = HttpResponseException.class)
  public void updateGroupSurveyAcronyms_should_handle_ValidationException_for_json_invalid() throws ValidationException, DataNotFoundException {
    when(surveyGroupService.updateSurveyGroupAcronyms(surveyGroupJson)).thenThrow(validationException);
    surveyGroupFacade.updateSurveyGroupAcronyms(surveyGroupJson);
  }

  @Test(expected = HttpResponseException.class)
  public void updateGroupSurveyAcronyms_should_handle_DataNotFoundException_for_json_invalid() throws ValidationException, DataNotFoundException {
    when(surveyGroupService.updateSurveyGroupAcronyms(surveyGroupJson)).thenThrow(dataNotFoundException);
    surveyGroupFacade.updateSurveyGroupAcronyms(surveyGroupJson);
  }

  @Test
  public void updateSurveyGroupName_should_invoke_updateSurveyGroupName_of_SurveyGroupService() throws ValidationException, DataNotFoundException {
    surveyGroupFacade.updateSurveyGroupName(surveyGroupNameDto);
    verify(surveyGroupService, times(1)).updateSurveyGroupName(surveyGroupNameDto);
  }

  @Test(expected = HttpResponseException.class)
  public void updateSurveyGroupName_should_handle_ValidationException_for_json_invalid() throws ValidationException, DataNotFoundException {
    when(surveyGroupService.updateSurveyGroupName(surveyGroupNameDto)).thenThrow(validationException);
    surveyGroupFacade.updateSurveyGroupName(surveyGroupNameDto);
  }

  @Test(expected = HttpResponseException.class)
  public void updateGroup_should_handle_DataNotFoundException_for_json_invalid() throws ValidationException, DataNotFoundException {
    when(surveyGroupService.updateSurveyGroupName(surveyGroupNameDto)).thenThrow(dataNotFoundException);
    surveyGroupFacade.updateSurveyGroupName(surveyGroupNameDto);
  }

  @Test
  public void deleteGroup_should_invoke_deleteGroup_of_SurveyGroupService() throws DataNotFoundException {
    surveyGroupFacade.deleteSurveyGroup(surveyGroupNameDto);
    verify(surveyGroupService, times(1)).deleteSurveyGroup(surveyGroupNameDto);
  }

  @Test(expected = HttpResponseException.class)
  public void deleteGroup_should_handle_DataNotFoundException_for_json_invalid() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(surveyGroupService, "deleteSurveyGroup", surveyGroupNameDto);
    surveyGroupFacade.deleteSurveyGroup(surveyGroupNameDto);
  }

  @Test
  public void getSurveyGroupsByUser_shold_return_list_of_SurveyGroups() {
    when(surveyGroupService.getSurveyGroupsByUser(USER_EMAIL)).thenReturn(surveyGroups);
    assertFalse(surveyGroupFacade.getSurveyGroupsByUser(USER_EMAIL).isEmpty());
  }
}