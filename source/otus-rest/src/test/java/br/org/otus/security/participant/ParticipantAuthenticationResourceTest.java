package br.org.otus.security.participant;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.rest.Response;
import br.org.otus.security.EncryptorResources;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.AuthenticationDto;
import br.org.otus.security.dtos.ParticipantSecurityAuthorizationDto;
import br.org.otus.security.dtos.ProjectAuthenticationDto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EncryptorResources.class)
public class ParticipantAuthenticationResourceTest {
  private static final String AUTHENTICATION_DTO_EMAIL = "otus@otus.com";
  private static final String HTTPSERVLET_REQUEST_IP = "143.54.220.57";
  private static final String ENCRYPT_IRREVERSIBLE = "123";
  private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZ"
    + "lcnJlaXJhQGdtYWlsLmNvbSJ9.6lNpZm4IhjINsUsWi5paX15dD7gPqlvapilx5Tf90NE";

  @InjectMocks
  private ParticipantAuthenticationResource participantAuthenticationResource;
  @Mock
  private HttpServletRequest request;
  @Mock
  private SecurityFacade securityFacade;

  private AuthenticationDto authenticationDto;
  private Response response;
  private ParticipantSecurityAuthorizationDto participantSecurityAuthorizationDto;

  @Before
  public void setUp() {
    mockStatic(EncryptorResources.class);
    authenticationDto = new AuthenticationDto();
    authenticationDto.setEmail(AUTHENTICATION_DTO_EMAIL);
    when(request.getRemoteAddr()).thenReturn(HTTPSERVLET_REQUEST_IP);
    response = new Response();
  }

  @Test
  public void method_Authenticate_should_return_response_participantSecurityAuthorizationDto() throws EncryptedException {
    when(EncryptorResources.encryptIrreversible(anyString())).thenReturn(ENCRYPT_IRREVERSIBLE);
    when(securityFacade.participantAuthentication(authenticationDto))
      .thenReturn(participantSecurityAuthorizationDto);
    String authenticationResponseExpected = response
      .buildSuccess(securityFacade.participantAuthentication(authenticationDto)).toJson();
    assertEquals(authenticationResponseExpected, participantAuthenticationResource.authenticate(authenticationDto, request));
  }

  @Test(expected = HttpResponseException.class)
  public void method_Authenticate_should_throw_EncryptedException() throws EncryptedException {
    when(EncryptorResources.encryptIrreversible(anyString())).thenThrow(new EncryptedException());
    participantAuthenticationResource.authenticate(authenticationDto, request);
  }

  @Test
  public void method_invalidate_should_call_securityFacade_invalidate_method() {
    participantAuthenticationResource.invalidate(authenticationDto, request);
    verify(securityFacade, times(1)).invalidateParticipantAuthentication(anyString(), anyString());
  }
}
