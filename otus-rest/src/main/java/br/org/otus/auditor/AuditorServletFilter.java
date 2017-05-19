package br.org.otus.auditor;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.io.IOUtils;
import org.ccem.auditor.model.LogEntry;
import org.ccem.auditor.model.SessionLog;
import org.ccem.auditor.service.AuditorService;

import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.services.SecurityContextService;

@WebFilter(filterName = "auditorFilter", urlPatterns = { "/v01/*" })
public class AuditorServletFilter implements Filter {

	@Inject
	private SecurityContextService securityContextService;
	@EJB
	private AuditorService auditorService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		ResettableStreamHttpServletRequest resettableStreamHttpServletRequest = new ResettableStreamHttpServletRequest(
				httpServletRequest);

		if (isLoggedMethod(httpServletRequest.getMethod())) {
			String url = resettableStreamHttpServletRequest.getRequestURL().toString();
			// TODO: Need to fix it.
			if (!url.contains("otus-rest/v01/upload")) {
				String body = IOUtils.toString(resettableStreamHttpServletRequest.getReader());
				String authorizationHeader = resettableStreamHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

				String token = readToken(authorizationHeader);
				SessionLog sessionLog = fetchSessionInformation(token);
				String remoteAddress = resettableStreamHttpServletRequest.getRemoteAddr();
				String method = httpServletRequest.getMethod().toString();

				auditorService.log(new LogEntry(remoteAddress, url, method, body, sessionLog));
				resettableStreamHttpServletRequest.resetInputStream();

			} else {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}
		filterChain.doFilter(resettableStreamHttpServletRequest, servletResponse);
		return;

	}

	@Override
	public void destroy() {
	}

	private Boolean isLoggedMethod(String method) {
		if (HttpMethod.POST.equals(method) || HttpMethod.DELETE.equals(method) || HttpMethod.PUT.equals(method)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	private String readToken(String authorizationHeader) {
		if (authorizationHeader != null) {
			return AuthorizationHeaderReader.readToken(authorizationHeader);
		} else {
			return "";
		}
	}

	private SessionLog fetchSessionInformation(String token) {
		SessionIdentifier session = securityContextService.getSession(token);

		if (session != null) {
			return session.buildLog();
		} else {
			return new SessionLog();
		}
	}
}
