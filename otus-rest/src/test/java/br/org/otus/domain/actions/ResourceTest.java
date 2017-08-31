package br.org.otus.domain.actions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.ccem.otus.exceptions.webservice.http.RestCallException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Resource.class)
public class ResourceTest {
	private static final String DOMAIN_URL = "api.domain.dev.ccem.ufrgs.br";
	private static final Integer RESPONSE_STATUS = 200;
	private static final Integer ERROR_STATUS = 404;
	private Resource resource;
	private UrlProvider domainUrlProvider;
	private String normalizeUrlExpected;
	@Mock
	private HttpResponse response;
	@Mock
	private StatusLine statusLine;
	@Mock
	HttpStatus httpStatus;

	@Before
	public void setUp() {
		resource = new Resource(DOMAIN_URL);
		domainUrlProvider = PowerMockito.spy(new UrlProvider());
		when(response.getStatusLine()).thenReturn(statusLine);
	}

	@Test
	public void method_normalizeUrl() throws Exception {
		normalizeUrlExpected = "api.domain.dev.ccem.ufrgs.br/otus-domain-rest/v01";
		PowerMockito.whenNew(UrlProvider.class).withNoArguments().thenReturn(domainUrlProvider);
		assertEquals(normalizeUrlExpected, resource.normalizeUrl(DOMAIN_URL));
		verify(domainUrlProvider).setUrl(DOMAIN_URL);
		verify(domainUrlProvider).setContext(UrlProvider.DEFAULT_CONTEXT);
		verify(domainUrlProvider).setVersion(UrlProvider.DEFAULT_VERSION);
	}

	@Test
	public void method_validationResponse_should_validate_response_with_httpStatus() throws RestCallException {
		when(statusLine.getStatusCode()).thenReturn(RESPONSE_STATUS);
		resource.validationResponse(response);		
	}
	
	@Test(expected = RestCallException.class)
	public void method_validationResponse_should_throws_RestCallException() throws RestCallException{
		when(statusLine.getStatusCode()).thenReturn(ERROR_STATUS);
		resource.validationResponse(response);
	}

}
