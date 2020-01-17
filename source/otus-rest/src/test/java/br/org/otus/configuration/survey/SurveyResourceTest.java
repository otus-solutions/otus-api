package br.org.otus.configuration.survey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.security.user.AuthorizationHeaderReader;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import br.org.otus.security.context.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.org.otus.survey.api.SurveyFacade;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AuthorizationHeaderReader.class)
public class SurveyResourceTest {
  private static final String USER_EMAIL = "otus@tus.com";
  private static final String ACRONYM = "USGC";
  private static final Boolean POSITIVE_RETURN = true;
  private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String SURVEY_ID = "5aff3edaaf11bb0d302be236";
  private static final String REQUIRED_EXT_ID = "{\"requiredExternalID\": true}";
  private static final String RESULT_UPDATE_REQUIRED_EXT_ID = "{\"data\":\"modifiedCount: 0\"}";

  @InjectMocks
  private SurveyResource surveyResource;
  @Mock
  private SurveyFacade surveyFacade;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private HttpServletRequest request;
  @Mock
  private AuthenticationData authenticationData;
  @Mock
  private SessionIdentifier sessionIdentifier;
  @Mock
  private UpdateSurveyFormTypeDto updateSurveyFormTypeDto;
  private List<SurveyForm> surveys;
  private SurveyTemplate surveyTemplate;
  private SurveyForm surveyForm;

  @Before
  public void setUp() {
    surveys = new ArrayList<SurveyForm>();
    surveyTemplate = new SurveyTemplate();
    surveyForm = new SurveyForm(surveyTemplate, USER_EMAIL);
    surveys.add(surveyForm);

    when(request.getHeader(Mockito.anyString())).thenReturn(TOKEN);
    PowerMockito.mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.readToken(TOKEN)).thenReturn(TOKEN);
    when(securityContext.getSession(TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(USER_EMAIL);
  }

  @Test
  public void method_getAllPermitted_should_return_response_in_surveyJson() {
    when(surveyFacade.listUndiscarded(USER_EMAIL)).thenReturn(surveys);
    assertTrue(surveyResource.getAllPermittedUndiscarded(request).contains(USER_EMAIL));
  }

  @Test
  public void method_getAll_should_return_response_in_surveyJson() {
    when(surveyFacade.listAllUndiscarded()).thenReturn(surveys);
    assertTrue(surveyResource.getAllUndiscarded(request).contains(USER_EMAIL));
  }

  @Test
  public void method_getByAcronym_should_return_response_in_surveyJson() {
    when(surveyFacade.findByAcronym(ACRONYM)).thenReturn(surveys);
    assertTrue(surveyResource.getByAcronym(ACRONYM).contains(USER_EMAIL));
  }

  @Test
  public void method_DeleteByAcronym_should_return_positive_answer_in_responseJson() {
    when(surveyFacade.deleteLastVersionByAcronym(ACRONYM)).thenReturn(POSITIVE_RETURN);
    assertTrue(surveyResource.deleteLastVersionSurveyByAcronym(ACRONYM).contains(POSITIVE_RETURN.toString()));
  }

  @Test
  public void method_UpdateSurveyFormType_should_return_positive_answer_in_responseJson() {
    when(surveyFacade.updateLastVersionSurveyType(updateSurveyFormTypeDto)).thenReturn(POSITIVE_RETURN);
    assertTrue(surveyResource.updateLastSurveySurveyType(ACRONYM, updateSurveyFormTypeDto)
      .contains(POSITIVE_RETURN.toString()));
  }

  @Test
  public void updateSurveyRequiredExternalIDMethodTest_should_compare_returnedResult_and_call_from_the_facade() {
    assertEquals(RESULT_UPDATE_REQUIRED_EXT_ID, surveyResource.updateSurveyRequiredExternalID(SURVEY_ID, REQUIRED_EXT_ID));
    Mockito.verify(surveyFacade, Mockito.times(1)).updateSurveyRequiredExternalID(SURVEY_ID, REQUIRED_EXT_ID);
  }
}
