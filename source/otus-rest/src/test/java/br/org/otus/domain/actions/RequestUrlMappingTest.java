package br.org.otus.domain.actions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestUrlMappingTest {

  private static final String SERVER_NAME = "api.domain.dev.ccem.ufrgs.br";
  private static final Integer SERVER_PORT = 8080;
  private static final String CONTEXT_PATH = "/otus-rest";
  private static final String SERVELET_PATH = "/v01/Authetication";
  @Spy
  private RequestUrlMapping requestUrlMapping;
  @Mock
  private HttpServletRequest request;
  private String projectRestUrlExpected;

  @Test
  public void method_getUrl_should_return_projectRestUrl() {
    projectRestUrlExpected = "http://api.domain.dev.ccem.ufrgs.br:8080/otus-rest/v01/Authetication";

    when(request.getServerName()).thenReturn(SERVER_NAME);
    when(request.getServerPort()).thenReturn(SERVER_PORT);
    when(request.getContextPath()).thenReturn(CONTEXT_PATH);
    when(request.getServletPath()).thenReturn(SERVELET_PATH);

    assertEquals(projectRestUrlExpected, requestUrlMapping.getUrl(request));
  }
}
