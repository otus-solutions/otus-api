package br.org.otus.settings;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.servlet.http.HttpServletRequest;

import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.api.SystemConfigFacade;
import br.org.otus.configuration.dto.DomainDto;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.domain.actions.DomainRegisterResource;
import br.org.otus.domain.actions.RequestUrlMapping;
import br.org.otus.domain.exceptions.DomainConnectionError;
import br.org.otus.project.dto.ProjectDto;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.rest.Response;

@RunWith(PowerMockRunner.class)
@PrepareForTest(InstallerResource.class)
public class InstallerResourceTest {
	private static final Boolean SYSTEM_CONFIG_FACADE_ISREADY = true;
	private static final String RESPONSE_BUILD_SUCESS = "{\"data\":true}";
	private static final String TOKEN = "a1c6ee0f-e332-4ec1-aec1-630089900f9d";
	private static final String PROJECT_NAME = "otus-api";
	@InjectMocks
	private InstallerResource installerResource = spy(new InstallerResource());
	@Mock
	private SystemConfigFacade systemConfigFacade;
	@Mock
	private OtusInitializationConfigDto systemConfigDto;
	@Mock
	private HttpServletRequest request;
	@Mock
	private DomainRegisterResource domainRegisterResource;
	@Mock
	private OtusInitializationConfigDto otusInitializationConfigDto;
	@Mock
	private OtusInitializationConfigDto initData;
	@Mock
	private DomainDto domainDto;
	@Mock
	private ProjectDto projectDto;
	@Mock
	private DomainConnectionError domainConnectionError;

	@Test
	public void method_ready_should_returns_responseBuildSucess() {
		when(systemConfigFacade.isReady()).thenReturn(SYSTEM_CONFIG_FACADE_ISREADY);
		assertEquals(RESPONSE_BUILD_SUCESS, installerResource.ready());
	}

	@Test
	public void method_config_should_returns_responseBuildSucess() throws Exception {
		doNothing().when(installerResource).registerProjectOnDomain(systemConfigDto, request, TOKEN);
		when(systemConfigFacade.buildToken()).thenReturn(TOKEN);
		assertEquals(RESPONSE_BUILD_SUCESS, installerResource.config(systemConfigDto, request));
		verify(systemConfigDto).encrypt();
		verify(systemConfigFacade).initConfiguration(systemConfigDto, TOKEN);
	}

	@Test(expected = HttpResponseException.class)
	public void method_config_should_throws_HttpResponseException() throws EncryptedException {
		doThrow(EncryptedException.class).when(systemConfigDto).encrypt();
		installerResource.config(systemConfigDto, request);
	}

	@Test
	public void method_validationEmail_should_returns_responseBuildSucess() throws EncryptedException {
		Response response = new Response();
		assertEquals(response.buildSuccess().toJson(), installerResource.validationEmail(otusInitializationConfigDto));
		verify(otusInitializationConfigDto).encrypt();
	}

	@Test(expected = HttpResponseException.class)
	public void method_validationEmail_should_throws_HttpResponseException() throws EncryptedException {
		doThrow(EncryptedException.class).when(otusInitializationConfigDto).encrypt();
		installerResource.validationEmail(otusInitializationConfigDto);
	}

	@Test
	public void method_registerProjectOnDomain_should_evocate_internal_methods() throws Exception {
		when(initData.getDomainDto()).thenReturn(domainDto);
		whenNew(DomainRegisterResource.class).withAnyArguments().thenReturn(domainRegisterResource);
		whenNew(DomainDto.class).withNoArguments().thenReturn(domainDto);
		when(initData.getProject()).thenReturn(projectDto);
		installerResource.registerProjectOnDomain(initData, request, TOKEN);
		verify(domainDto).setDomainRestUrl(domainRegisterResource.DOMAIN_URL);
		verify(domainRegisterResource).registerProject(RequestUrlMapping.getUrl(request),
				initData.getProject().getProjectName(), TOKEN);
	}

	@Test(expected = HttpResponseException.class)
	public void method_registerProjectOnDomain_should_throws_domainConnectionError()
			throws Exception, DomainConnectionError {
		when(initData.getDomainDto()).thenReturn(domainDto);
		whenNew(DomainRegisterResource.class).withAnyArguments().thenReturn(domainRegisterResource);
		when(initData.getProject()).thenReturn(projectDto);
		when(projectDto.getProjectName()).thenReturn(PROJECT_NAME);
		PowerMockito.doThrow(domainConnectionError).when(domainRegisterResource, "registerProject", any(), any(),
				any());
		installerResource.registerProjectOnDomain(initData, request, TOKEN);
	}
}