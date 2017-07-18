package br.org.otus.domain.actions;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DomainRegisterResource.class, HttpClientBuilder.class })
public class DomainRegisterResourceTest {
	
	public static final String CONTENT_TYPE = "content-type";
	public static final String CONTENT_TYPE_VALUE = "application/json";
	public static final String REGISTER_REST_PATH = "/otus";
	public static final String DOMAIN_REST_URL = "https://domain.hmg.ccem.ufrgs.br";
	public static final String PROJECT_REST_URL = "https://api-otus.dev.ccem.ufrgs.br";
	public static final String PROJECT_NAME = "OtusLocal";
	public static final String TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";
	public static final Integer RESPONSE_STATUS = 200;

	@Mock
	HttpClientBuilder httpClientBuilder;
	@Mock
	CloseableHttpClient httpClient;
	@Mock
	CloseableHttpResponse response;
	@Mock
	StatusLine statusLineResponse;

	private DomainRegisterResource domainRegisterResourceSpy;
	@Mock
	private DomainRegisterResource domainRegisterResourceException;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void method_RegisterProject_should_call_validationResponse() throws Exception {
		
		domainRegisterResourceSpy = spy(new DomainRegisterResource(DOMAIN_REST_URL));
		mockStatic(HttpClientBuilder.class);
		when(HttpClientBuilder.class, "create").thenReturn(httpClientBuilder);
		when(httpClientBuilder.build()).thenReturn(httpClient);

		when(response.getStatusLine()).thenReturn(statusLineResponse);
		when(statusLineResponse.getStatusCode()).thenReturn(RESPONSE_STATUS);
		when(httpClient.execute(any(HttpPost.class))).thenReturn(response);

		domainRegisterResourceSpy.registerProject(PROJECT_REST_URL, PROJECT_NAME, TOKEN);
		verify(domainRegisterResourceSpy).validationResponse(response);
	}

	

}
