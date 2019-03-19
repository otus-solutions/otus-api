package br.org.otus.survey.services;

import br.org.otus.survey.SurveyDaoBean;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.form.SurveyFormType;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.identity.Identity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

@PrepareForTest(SurveyForm.class)
@RunWith(PowerMockRunner.class)
public class SurveyServiceBeanTest {
    private static final String ACRONYM = "DIEC";
    private static final String ACRONYM_EMPTY = "";
    private static final String SURVEY_SERIALIZE = "{survey:'survey'}";
    private static final String ACRONYM_NULL = null;
    private static final Integer LATEST_VERSION = 1;
    private static final Integer VERSION = 1;


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
    private SurveyTemplate surveyTemplate;


    @Before
    public void setup() throws DataNotFoundException {
        PowerMockito.mockStatic(SurveyForm.class);
        PowerMockito.when(SurveyForm.serialize(survey)).thenReturn(SURVEY_SERIALIZE);

        PowerMockito.when(updateSurveyFormTypeDtoInvalid.isValid()).thenReturn(false);
        PowerMockito.when(updateSurveyFormTypeDtoValid.isValid()).thenReturn(true);

        updateSurveyFormTypeDtoValid.acronym = ACRONYM;

        updateSurveyFormTypeDtoValid.newSurveyFormType = SurveyFormType.FORM_INTERVIEW;

        PowerMockito.when(surveyDaoBean.updateLastVersionSurveyType(updateSurveyFormTypeDtoValid.acronym,
                updateSurveyFormTypeDtoValid.newSurveyFormType.toString())).thenReturn(true);

        PowerMockito.when(surveyDaoBean.deleteLastVersionByAcronym(ACRONYM)).thenReturn(true);
        PowerMockito.when(surveyDaoBean.getLastVersionByAcronym(ACRONYM)).thenReturn(lastVersionSurvey);


        surveyTemplate.identity = new Identity();
        surveyTemplate.identity.acronym = ACRONYM;

        PowerMockito.when(survey.getSurveyTemplate()).thenReturn(surveyTemplate);
        PowerMockito.when(lastVersionSurvey.getVersion()).thenReturn(LATEST_VERSION);

    }

    //save survey unit tests
    @Test
    public void saveSurvey_shoud_call_method_validateSurvey() throws AlreadyExistException, DataNotFoundException {
        service.saveSurvey(survey);
        Mockito.verify(surveyValidatorServiceBean).validateSurvey(surveyDaoBean, survey);
    }

    @Test
    public void saveSurvey_should_call_method_persist() throws AlreadyExistException, DataNotFoundException {
        service.saveSurvey(survey);

        Mockito.verify(surveyDaoBean).persist(survey);
    }

    @Test
    public void should_set_the_survey_version_to_latest_plus_one() throws AlreadyExistException, DataNotFoundException {
        service.saveSurvey(survey).getVersion();
        Mockito.verify(survey).setVersion(LATEST_VERSION + 1);
    }

    @Test
    public void should_discard_the_previous_latest_version_if_exists() throws AlreadyExistException, DataNotFoundException {
        service.saveSurvey(survey).getVersion();
        Mockito.verify(surveyDaoBean).discardSurvey(lastVersionSurvey.getSurveyID());
    }

    @Test
    public void should_set_the_survey_version_to_1_when_is_the_first() throws AlreadyExistException, DataNotFoundException {
        PowerMockito.when(surveyDaoBean.getLastVersionByAcronym(ACRONYM)).thenReturn(null);
        service.saveSurvey(survey).getVersion();
        Mockito.verify(survey).setVersion(1);
    }

    @Test
    public void should_set_survey_objectId_from_persistence() throws AlreadyExistException, DataNotFoundException {
        ObjectId objectId = new ObjectId();

        PowerMockito.when(surveyDaoBean.persist(survey)).thenReturn(objectId);
        service.saveSurvey(survey).getVersion();
        Mockito.verify(survey).setSurveyID(objectId);
    }


    //list method unit tests
    @Test
    public void listUndiscarded_should_call_surveyDao_find() {
        service.listUndiscarded("");
        Object[] objects = new Object[0];
        Mockito.verify(surveyDaoBean).findUndiscarded(objects,"");
    }

    @Test
    public void findByAcronym_should_call_method_findByAcronym_by_surveyDao() {
        service.findByAcronym(ACRONYM);
        Mockito.verify(surveyDaoBean).findByAcronym(ACRONYM);

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
    public void deleteLastVersionByAcronym_should_returns_positive_answer_case_acronym_not_be_null_or_empty()
            throws ValidationException, DataNotFoundException {
        assertTrue(surveyDaoBean.deleteLastVersionByAcronym(ACRONYM));
    }

  @Test
  public void get_should_call_method_findByAcronym_by_surveyDao() throws DataNotFoundException {
    service.get(ACRONYM, VERSION);
    Mockito.verify(surveyDaoBean).get(ACRONYM, VERSION);

  }

  @Test
  public void listSurveyVersions_should_call_method_getSurveyVersions_by_surveyDao() throws DataNotFoundException {
    service.listSurveyVersions(ACRONYM);
    Mockito.verify(surveyDaoBean).getSurveyVersions(ACRONYM);

  }

  @Test
  public void listAcronyms_should_call_method_listAcronyms_by_surveyDao() throws DataNotFoundException {
    service.listAcronyms();
    Mockito.verify(surveyDaoBean).listAcronyms();

  }

}
