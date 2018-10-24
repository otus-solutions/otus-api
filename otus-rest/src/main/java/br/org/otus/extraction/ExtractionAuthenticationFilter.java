package br.org.otus.extraction;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import br.org.otus.response.info.ExtractionNotAuthorized;

@Provider
@SecuredExtraction
public class ExtractionAuthenticationFilter implements ContainerRequestFilter {

	@Inject
	private ExtractionSecurityService extractionSecurityService;

	@Context
	private HttpServletRequest requestContext;
	
	public static final String HEADER = "X-Real-IP";

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		
		String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String ipAddress = requestContext.getHeader(HEADER);
		try {
			Boolean authorization = extractionSecurityService.validateSecurityCredentials(authorizationToken, ipAddress);
			validateAuthorization(authorization);
		} catch (Exception e) {
			containerRequestContext.abortWith(ExtractionNotAuthorized.build().toResponse());
		}
	}

	private void validateAuthorization(Boolean authorization) throws Exception {
		if (!Boolean.TRUE.equals(authorization)) {
			throw new Exception("Extraction not authorized");
		}
	}
}
