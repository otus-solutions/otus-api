package br.org.otus.security.api;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.security.dtos.AuthenticationDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.security.dtos.ProjectAuthenticationDto;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
import br.org.otus.security.services.SecurityService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class SecurityFacadeTest {
  private static final String REQUEST_ADDRESS = "http://api.domain.dev.ccem.ufrgs.br:8080";
  private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String EMAIL = "teste@gmail.com";
  @InjectMocks
  private SecurityFacade securityFacade;
  @Mock
  private SecurityService securityService;
  @Mock
  private AuthenticationDto authenticationDto;
  @Mock
  private UserSecurityAuthorizationDto userSecurityAuthorizationDto;
  @Mock
  private ProjectAuthenticationDto projectAuthenticationDto;
  @Mock
  private PasswordResetRequestDto passwordResetRequestDto;

  @Test
  public void method_UserAuthentication_should_return_userSecurityAuthorizationDto()
    throws TokenException, AuthenticationException {
    Mockito.when(securityService.authenticate(authenticationDto)).thenReturn(userSecurityAuthorizationDto);
    assertTrue(securityFacade.userAuthentication(authenticationDto,
      REQUEST_ADDRESS) instanceof UserSecurityAuthorizationDto);
    verify(authenticationDto).setRequestAddress(REQUEST_ADDRESS);
  }

  @Test(expected = HttpResponseException.class)
  public void method_UserAuthentication_should_capture_AuthenticationException()
    throws TokenException, AuthenticationException {
    when(securityService.authenticate(authenticationDto)).thenThrow(AuthenticationException.class);
    securityFacade.userAuthentication(authenticationDto, REQUEST_ADDRESS);
    verify(authenticationDto).setRequestAddress(REQUEST_ADDRESS);
  }

  @Test(expected = HttpResponseException.class)
  public void method_UserAuthentication_should_capture_TokenException()
    throws TokenException, AuthenticationException {
    when(securityService.authenticate(authenticationDto)).thenThrow(TokenException.class);
    securityFacade.userAuthentication(authenticationDto, REQUEST_ADDRESS);
    verify(authenticationDto).setRequestAddress(REQUEST_ADDRESS);
  }

  @Test
  public void method_ProjectAuthentication_should_return_projectAuthentication()
    throws TokenException, AuthenticationException {
    when(securityService.projectAuthenticate(projectAuthenticationDto)).thenReturn(TOKEN);
    assertEquals(TOKEN, securityFacade.projectAuthentication(projectAuthenticationDto, REQUEST_ADDRESS));
    verify(projectAuthenticationDto).setRequestAddress(REQUEST_ADDRESS);
  }

  @Test(expected = HttpResponseException.class)
  public void method_ProjectAuthentication_should_capture_AuthenticationException()
    throws TokenException, AuthenticationException {
    when(securityService.projectAuthenticate(projectAuthenticationDto)).thenThrow(AuthenticationException.class);
    assertEquals(TOKEN, securityFacade.projectAuthentication(projectAuthenticationDto, REQUEST_ADDRESS));
    verify(projectAuthenticationDto).setRequestAddress(REQUEST_ADDRESS);
  }

  @Test(expected = HttpResponseException.class)
  public void method_ProjectAuthentication_should_capture_TokenException()
    throws TokenException, AuthenticationException {
    when(securityService.projectAuthenticate(projectAuthenticationDto)).thenThrow(TokenException.class);
    assertEquals(TOKEN, securityFacade.projectAuthentication(projectAuthenticationDto, REQUEST_ADDRESS));
    verify(projectAuthenticationDto).setRequestAddress(REQUEST_ADDRESS);
  }

  @Test
  public void method_Invalidate_evocate_invalidate_of_securityService() {
    securityFacade.invalidate(TOKEN);
    verify(securityService).invalidate(TOKEN);

  }

  @Test
  public void method_requestPasswordReset_should_call_getPasswordResetToken() throws TokenException, DataNotFoundException {
    securityFacade.requestPasswordReset(passwordResetRequestDto);
    when(securityService.getPasswordResetToken(passwordResetRequestDto)).thenReturn(TOKEN);
    verify(securityService, times(1)).getPasswordResetToken(passwordResetRequestDto);
  }

  @Test(expected = HttpResponseException.class)
  public void method_requestPasswordReset_should_Throw_TokenException() throws TokenException, DataNotFoundException {
    when(securityService.getPasswordResetToken(passwordResetRequestDto)).thenThrow(new TokenException(new Throwable(TOKEN)));
    securityFacade.requestPasswordReset(passwordResetRequestDto);
  }

  @Test
  public void method_validatePasswordResetRequest_should_call_validatePasswordReset() throws TokenException {
    securityFacade.validatePasswordResetRequest(TOKEN);
    verify(securityService, times(1)).validatePasswordReset(TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void method_validatePasswordResetRequest_should_Throw_TokenException() throws TokenException {
    doThrow(new TokenException()).when(securityService).validatePasswordReset(TOKEN);
    securityFacade.validatePasswordResetRequest(TOKEN);
  }

  @Test
  public void method_removePasswordResetRequests_should_call_securityService_removePasswordResetRequests() {
    securityFacade.removePasswordResetRequests(EMAIL);
    verify(securityService, times(1)).removePasswordResetRequests(EMAIL);
  }

  @Test
  public void method_getRequestEmail_should_call_getRequestEmail() throws DataNotFoundException {
    securityFacade.getRequestEmail(TOKEN);
    verify(securityService, times(1)).getRequestEmail(TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void method_getRequestEmail_should_Throw_TokenException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(securityService).getRequestEmail(TOKEN);
    securityFacade.getRequestEmail(TOKEN);
  }

  @Test
  public void method_participantAuthentication_should_call_participantAuthenticate() throws DataNotFoundException, TokenException, AuthenticationException {
    AuthenticationDto authenticationDto = new AuthenticationDto();
    securityFacade.participantAuthentication(authenticationDto);
    verify(securityService, times(1)).participantAuthenticate(authenticationDto);
  }

  @Test(expected = HttpResponseException.class)
  public void method_participantAuthentication_should_Throw_TokenException() throws DataNotFoundException, TokenException, AuthenticationException {
    AuthenticationDto authenticationDto = new AuthenticationDto();
    doThrow(new TokenException()).when(securityService).participantAuthenticate(authenticationDto);
    securityFacade.participantAuthentication(authenticationDto);
  }

  @Test(expected = HttpResponseException.class)
  public void method_participantAuthentication_should_Throw_AuthenticationException() throws DataNotFoundException, TokenException, AuthenticationException {
    AuthenticationDto authenticationDto = new AuthenticationDto();
    doThrow(new AuthenticationException()).when(securityService).participantAuthenticate(authenticationDto);
    securityFacade.participantAuthentication(authenticationDto);
  }

  @Test
  public void method_invalidateParticipantAuthentication_should_call_securityService_invalidateParticipantAuthenticate() throws DataNotFoundException, TokenException, AuthenticationException {
    securityFacade.invalidateParticipantAuthentication("email", "token");
    verify(securityService, times(1)).invalidateParticipantAuthenticate("email", "token");
  }

  @Test
  public void method_requestParticipantPasswordReset_should_evoke_getParticipantPasswordResetToken_by_securityService() throws TokenException, DataNotFoundException {
    securityFacade.requestParticipantPasswordReset(passwordResetRequestDto);
    Mockito.verify(securityService, Mockito.times(1)).registerParticipantPasswordResetToken(passwordResetRequestDto);
  }

  @Test(expected = HttpResponseException.class)
  public void method_requestParticipantPasswordReset_should_catch_exception() throws TokenException, DataNotFoundException {
    Throwable mockTokenException = spy(new TokenException());
    Mockito.doThrow( mockTokenException).when(securityService).registerParticipantPasswordResetToken(passwordResetRequestDto);
    when(mockTokenException.getCause()).thenReturn(mockTokenException);
    when(mockTokenException.getMessage()).thenReturn("expectedFail");

    securityFacade.requestParticipantPasswordReset(passwordResetRequestDto);
  }
}
