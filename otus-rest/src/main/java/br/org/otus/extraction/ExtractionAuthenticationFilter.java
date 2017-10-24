package br.org.otus.extraction;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import br.org.otus.response.info.Authorization;

@SecuredExtraction
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ExtractionAuthenticationFilter implements ContainerRequestFilter {

	@Inject
	private ExtractionSecurityService extractionSecurityService;

	@Context
	private HttpServletRequest requestContext;

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String ipAddress = requestContext.getRemoteAddr();
		try {
			extractionSecurityService.validateSecurityCredentials(authorizationToken, ipAddress);
		} catch (Exception e) {
			containerRequestContext.abortWith(Authorization.build().toResponse());
		}
	}
}
