package br.org.otus.survey.services;

import br.org.otus.survey.SurveyDaoBean;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.persistence.SurveyJumpMapDao;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.form.SurveyFormType;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.identity.Identity;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@PrepareForTest(SurveyForm.class)
@RunWith(PowerMockRunner.class)
public class SurveyServiceBeanTest {

  private static final String ACRONYM = "ABC";
  private static final String ACRONYM_EMPTY = "";
  private static final String SURVEY_SERIALIZE = "{survey:'survey'}";
  private static final String ACRONYM_NULL = null;
  private static final Integer LATEST_VERSION = 1;
  private static final Integer VERSION = 1;
  private static final String SURVEY_ID = "5aff3edaaf11bb0d302be236";
  private static final String REQUIRED_EXT_ID = "{\"requiredExternalID\": true}";

  @InjectMocks
  private SurveyServiceBean service;
  @Mock
  private SurveyForm survey;
  @Mock
  private SurveyForm lastVersionSurvey;
  @Mock
  private SurveyDaoBean surveyDaoBean;
  @Mock
  private SurveyValidatorServiceBean surveyValidatorServiceBean;
  @Mock
  private UpdateSurveyFormTypeDto updateSurveyFormTypeDtoInvalid;
  @Mock
  private UpdateSurveyFormTypeDto updateSurveyFormTypeDtoValid;
  @Mock
  private SurveyJumpMap surveyJumpMap;
  @Mock
  private SurveyJumpMapDao surveyJumpMapDao;
  @Mock
  private SurveyTemplate surveyTemplate;
  @Mock
  private UpdateResult updateResult;
  @Mock
  private ActivityAccessPermissionService activityAccessPermissionService;


  @Before
  public void setup() throws DataNotFoundException {
    mockStatic(SurveyForm.class);
    when(SurveyForm.serialize(survey)).thenReturn(SURVEY_SERIALIZE);

    when(updateSurveyFormTypeDtoInvalid.isValid()).thenReturn(false);
    when(updateSurveyFormTypeDtoValid.isValid()).thenReturn(true);

    updateSurveyFormTypeDtoValid.acronym = ACRONYM;
    updateSurveyFormTypeDtoValid.newSurveyFormType = SurveyFormType.FORM_INTERVIEW;

    when(surveyDaoBean.updateLastVersionSurveyType(updateSurveyFormTypeDtoValid.acronym,
      updateSurveyFormTypeDtoValid.newSurveyFormType.toString())).thenReturn(true);

    when(surveyDaoBean.deleteLastVersionByAcronym(ACRONYM)).thenReturn(true);
    when(surveyDaoBean.getLastVersionByAcronym(ACRONYM)).thenReturn(lastVersionSurvey);

    surveyTemplate.identity = new Identity();
    surveyTemplate.identity.acronym = ACRONYM;

    when(survey.getSurveyTemplate()).thenReturn(surveyTemplate);
    when(lastVersionSurvey.getVersion()).thenReturn(LATEST_VERSION);

    when(surveyDaoBean.getLastVersionByAcronym(ACRONYM)).thenReturn(lastVersionSurvey);
    when(surveyDaoBean.updateSurveyRequiredExternalID(Mockito.any(), Mockito.any())).thenReturn(updateResult);
  }

  @Test
  public void saveSurvey_method_should_call_method_validateSurvey() throws AlreadyExistException, DataNotFoundException {
    when(activityAccessPermissionService.get(ACRONYM, VERSION)).thenThrow(new DataNotFoundException());
    service.saveSurvey(survey);
    verify(surveyValidatorServiceBean).validateSurvey(surveyDaoBean, survey);
  }

  @Test
  public void saveSurvey_method_should_call_method_persist() throws AlreadyExistException, DataNotFoundException {
    when(activityAccessPermissionService.get(ACRONYM, VERSION)).thenThrow(new DataNotFoundException());
    service.saveSurvey(survey);
    verify(surveyDaoBean).persist(survey);
  }

  @Test
  public void saveSurvey_method_should_set_the_survey_version_to_latest_plus_one() throws AlreadyExistException, DataNotFoundException {
    when(activityAccessPermissionService.get(ACRONYM, VERSION)).thenThrow(new DataNotFoundException());
    service.saveSurvey(survey).getVersion();
    verify(survey).setVersion(LATEST_VERSION + 1);
  }

  @Test
  public void saveSurvey_method_should_discard_the_previous_latest_version_if_exists() throws AlreadyExistException, DataNotFoundException {
    when(activityAccessPermissionService.get(ACRONYM, VERSION)).thenThrow(new DataNotFoundException());
    service.saveSurvey(survey).getVersion();
    verify(surveyDaoBean).discardSurvey(lastVersionSurvey.getSurveyID());
  }

  @Test
  public void saveSurvey_method_should_set_the_survey_version_to_1_when_is_the_first() throws AlreadyExistException, DataNotFoundException {
    when(activityAccessPermissionService.get(ACRONYM, VERSION)).thenThrow(new DataNotFoundException());
    when(surveyDaoBean.getLastVersionByAcronym(ACRONYM)).thenReturn(null);
    service.saveSurvey(survey).getVersion();
    verify(survey).setVersion(1);
  }

  @Test
  public void saveSurvey_method_should_set_survey_objectId_from_persistence() throws AlreadyExistException, DataNotFoundException {
    when(activityAccessPermissionService.get(ACRONYM, VERSION)).thenThrow(new DataNotFoundException());
    ObjectId objectId = new ObjectId();
    when(surveyDaoBean.persist(survey)).thenReturn(objectId);
    service.saveSurvey(survey).getVersion();
    verify(survey).setSurveyID(objectId);
  }

  @Test
  public void saveSurvey_method_should_call_create_method_of_activityAccessPermissionService() throws AlreadyExistException, DataNotFoundException {
    when(activityAccessPermissionService.get(ACRONYM, VERSION)).thenReturn(new ActivityAccessPermission(ACRONYM, VERSION));
    ActivityAccessPermissionService mockClass = mock(ActivityAccessPermissionService.class);
    doNothing().when(mockClass).create(Mockito.any());
    service.saveSurvey(survey);
    verify(activityAccessPermissionService, Mockito.times(1)).create(Mockito.any());
  }


  @Test
  public void listUndiscarded_should_call_surveyDao_find() {
    service.listUndiscarded("");
    verify(surveyDaoBean).findUndiscarded(new ArrayList<>(), "");
  }

  @Test
  public void listAllUndiscarded_should_call_surveyDao_find() {
    service.listAllUndiscarded();
    verify(surveyDaoBean).findAllUndiscarded();
  }

  @Test
  public void findByAcronym_should_call_method_findByAcronym_by_surveyDao() {
    service.findByAcronym(ACRONYM);
    verify(surveyDaoBean).findByAcronym(ACRONYM);
  }


  @Test(expected = ValidationException.class)
  public void updateLastVersionSurveyType_should_throw_exception_case_updateSurveyFormTypeDto_invalid()
    throws org.ccem.otus.exceptions.webservice.validation.ValidationException, DataNotFoundException {
    service.updateLastVersionSurveyType(updateSurveyFormTypeDtoInvalid);
  }

  @Test
  public void updateLastVersionSurveyType_should_call_and_return_method_updateSurveyFormType_case_updateSurveyFormTypeDto_is_valid()
    throws ValidationException, DataNotFoundException {
    assertTrue(service.updateLastVersionSurveyType(updateSurveyFormTypeDtoValid));
  }


  @Test(expected = ValidationException.class)
  public void deleteLastVersionByAcronym_should_throw_ValidationException_case_acronym_to_be_empty() throws ValidationException, DataNotFoundException {
    service.deleteLastVersionByAcronym(ACRONYM_EMPTY);
  }

  @Test(expected = ValidationException.class)
  public void deleteLastVersionByAcronym_should_throw_ValidationException_case_acronym_to_be_null() throws ValidationException, DataNotFoundException {
    service.deleteLastVersionByAcronym(ACRONYM_NULL);
  }

  @Test
  public void deleteLastVersionByAcronym_should_call_same_method_of_surveyDaoBean_case_acronym_not_be_null_or_empty() throws ValidationException, DataNotFoundException {
    service.deleteLastVersionByAcronym(ACRONYM);
    verify(surveyDaoBean, Mockito.times(1)).deleteLastVersionByAcronym(ACRONYM);
  }


  @Test
  public void get_should_call_method_findByAcronym_by_surveyDao() throws DataNotFoundException {
    service.get(ACRONYM, VERSION);
    verify(surveyDaoBean).get(ACRONYM, VERSION);
  }

  @Test
  public void listSurveyVersions_should_call_method_getSurveyVersions_by_surveyDao() throws DataNotFoundException {
    service.listSurveyVersions(ACRONYM);
    verify(surveyDaoBean).getSurveyVersions(ACRONYM);
  }

  @Test
  public void listAcronyms_should_call_method_listAcronyms_by_surveyDao() throws DataNotFoundException {
    service.listAcronyms();
    verify(surveyDaoBean).listAcronyms();
  }

  @Test
  public void updateSurveyRequiredExternalIDMethod_should_return_instance_by_UpdateResult() throws JSONException, DataNotFoundException {
    assertTrue(service.updateSurveyRequiredExternalID(SURVEY_ID, REQUIRED_EXT_ID) instanceof UpdateResult);
  }

  @Test
  public void createSurveyJumpMap_should_persist_surveyJumpMap() throws AlreadyExistException, DataNotFoundException {
    when(surveyDaoBean.createJumpMap(survey.getSurveyTemplate().identity.acronym, survey.getVersion())).thenReturn(surveyJumpMap);
    service.createSurveyJumpMap(survey);
    verify(surveyJumpMapDao).persist(surveyJumpMap);
  }
}
