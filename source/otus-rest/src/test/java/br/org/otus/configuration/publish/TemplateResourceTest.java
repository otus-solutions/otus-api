package br.org.otus.configuration.publish;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.servlet.http.HttpServletRequest;

import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.survey.api.SurveyFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AuthorizationHeaderReader.class, SurveyTemplate.class })
public class TemplateResourceTest {
	private static final String USER_EMAIL = "otus@otus.com";
	private static final String TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";

	@InjectMocks
	private TemplateResource templateResource;
	@Mock
	private SurveyFacade surveyFacade;
	@Mock
	private SecurityContext securityContext;
	@Mock
	private HttpServletRequest request;
	@Mock
	private SessionIdentifier sessionIdentifier;
	@Mock
	private AuthenticationData authenticationData;
	private String template;
	private SurveyTemplate surveyTemplate;
	private SurveyForm surveyForm;
	private String expectedResponse;

	@Before
	public void setUp() {
		template = SurveyTemplateFactory.create().toString();
		surveyTemplate = SurveyTemplate.deserialize(template);
		surveyForm = new SurveyForm(surveyTemplate, USER_EMAIL);
		expectedResponse = new Response().setData(surveyForm).toJson();
	}

	@Test
	public void method_post_should_responseJson_of_SurveyForm() {
		when(request.getHeader(Mockito.anyString())).thenReturn(TOKEN);
		mockStatic(AuthorizationHeaderReader.class);
		when(AuthorizationHeaderReader.readToken(TOKEN)).thenReturn(TOKEN);
		when(securityContext.getSession(TOKEN)).thenReturn(sessionIdentifier);
		when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
		when(authenticationData.getUserEmail()).thenReturn(USER_EMAIL);
		mockStatic(SurveyTemplate.class);
		when(SurveyTemplate.deserialize(template)).thenReturn(surveyTemplate);
		when(surveyFacade.publishSurveyTemplate(surveyTemplate, USER_EMAIL)).thenReturn(surveyForm);

		assertEquals(expectedResponse, templateResource.post(request, template));

	}

}
