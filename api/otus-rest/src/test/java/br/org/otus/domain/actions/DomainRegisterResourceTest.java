package br.org.otus.domain.actions;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.ccem.otus.exceptions.webservice.http.RestCallException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.domain.actions.DomainRegisterResource.OtusProjectDto;
import br.org.otus.domain.exceptions.DomainConnectionError;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DomainRegisterResource.class, HttpClientBuilder.class })
public class DomainRegisterResourceTest {

	private static final String CONTENT_TYPE = "content-type";
	private static final String CONTENT_TYPE_VALUE = "application/json";
	private static final String REGISTER_REST_PATH = "/otus";
	private static final String DOMAIN_REST_URL = "https://domain.hmg.ccem.ufrgs.br";
	private static final String PROJECT_REST_URL = "https://api-otus.dev.ccem.ufrgs.br";
	private static final String PROJECT_NAME = "OtusLocal";
	private static final String TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";
	private static final Integer RESPONSE_STATUS = 200;

	@Mock
	private HttpClientBuilder httpClientBuilder;
	@Mock
	private CloseableHttpClient httpClient;
	@Mock
	private CloseableHttpResponse response;
	@Mock
	private StatusLine statusLineResponse;
	private DomainRegisterResource domainRegisterResourceSpy;

	@Before
	public void setUp() throws Exception {
		domainRegisterResourceSpy = spy(new DomainRegisterResource(DOMAIN_REST_URL));
		mockStatic(HttpClientBuilder.class);
		when(HttpClientBuilder.class, "create").thenReturn(httpClientBuilder);
		when(httpClientBuilder.build()).thenReturn(httpClient);

		when(response.getStatusLine()).thenReturn(statusLineResponse);
		when(statusLineResponse.getStatusCode()).thenReturn(RESPONSE_STATUS);
		when(httpClient.execute(any(HttpPost.class))).thenReturn(response);
	}

	@Test
	public void method_RegisterProject_should_call_validationResponse() throws Exception {
		domainRegisterResourceSpy.registerProject(PROJECT_REST_URL, PROJECT_NAME, TOKEN);
		verify(domainRegisterResourceSpy).validationResponse(response);
	}

	@Test(expected = DomainConnectionError.class)
	public void method_RegisterProject_should_captured_RestCallException()
			throws RestCallException, DomainConnectionError {
		doThrow(RestCallException.class).when(domainRegisterResourceSpy).validationResponse(response);
		domainRegisterResourceSpy.registerProject(PROJECT_REST_URL, PROJECT_NAME, TOKEN);
	}

	@Test(expected = DomainConnectionError.class)
	public void method_RegisterProject_should_captured_JSONException() throws Exception {
		whenNew(OtusProjectDto.class).withArguments(PROJECT_REST_URL, PROJECT_NAME, TOKEN)
				.thenThrow(JSONException.class);
		domainRegisterResourceSpy.registerProject(PROJECT_REST_URL, PROJECT_NAME, TOKEN);
	}

	@Test(expected = DomainConnectionError.class)
	public void method_RegisterProject_should_captured_IOException() throws Exception {
		doThrow(IOException.class).when(domainRegisterResourceSpy).validationResponse(response);		
		domainRegisterResourceSpy.registerProject(PROJECT_REST_URL, PROJECT_NAME, TOKEN);
	}

}
