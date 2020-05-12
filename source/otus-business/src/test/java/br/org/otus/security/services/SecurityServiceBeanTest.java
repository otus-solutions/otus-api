package br.org.otus.security.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.invokeMethod;

import javax.persistence.NoResultException;

import br.org.otus.security.dtos.ParticipantSecurityAuthorizationDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import br.org.otus.model.User;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
import br.org.otus.system.SystemConfig;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.persistence.UserDao;

import java.security.SecureRandom;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(SecurityServiceBean.class)
public class SecurityServiceBeanTest {

  private static final String EMAIL = "otus@otus.com";
  private static final String PASSWORD = "TXUEOePzmEg0XG73TvPXGeNOcRE";
  private static final String OTHER_PASSWORD = "TXUEOePzmEg0XG73TvPXGeNOcR";
  private static final Boolean POSITIVE_ANSWER = true;
  private static final Boolean NEGATIVE_ANSWER = false;
  private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String JWT_SIGNED_SERIALIZED = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoiY2xpZW50IiwiaXNzIjoiT3R1cyBMb2NhbCJ9.2IySh22tfoVuCefGFxhGJgR6Q83wpJUWW4Efv376XKk";

  @InjectMocks
  private SecurityServiceBean securityServiceBean = spy(new SecurityServiceBean());
  @Mock
  private PasswordResetContextService passwordResetContextService;
  @Mock
  private UserDao userDao;
  @Mock
  private AuthenticationData authenticationData;
  @Mock
  private SystemConfigDaoBean systemConfigDao;
  @Mock
  private SecurityContextService securityContextService;
  @Mock
  private User user;
  @Mock
  private FieldCenter fieldCenter;
  @Mock
  private SystemConfig systemConfig;
  @Mock
  private PasswordResetRequestDto passwordResetRequestDto;
  @Mock
  private ParticipantSecurityAuthorizationDto participantSecurityAuthorizationDto;
  @Mock
  private ParticipantDao participantDao;
  @Mock
  private Participant participant;
  @Mock
  private SessionIdentifier sessionIdentifier;
  private UserSecurityAuthorizationDto userSecurityAuthorizationDto;
  private byte[] secretKey;

  @Before
  public void setUp() throws Exception {
    when(authenticationData.getUserEmail()).thenReturn(EMAIL);
    when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    userSecurityAuthorizationDto = spy(new UserSecurityAuthorizationDto());
    when(systemConfigDao.fetchSystemConfig()).thenReturn(systemConfig);
    when(authenticationData.getKey()).thenReturn(PASSWORD);

  }

  @Test
  public void method_authenticate_should_return_UserSecurityAuthorizationDto() throws Exception {
    byte[] sharedSecret = new byte[32];
    when(authenticationData.getUserEmail()).thenReturn(EMAIL);
    when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    when(authenticationData.getKey()).thenReturn(JWT_SIGNED_SERIALIZED);
    when(user.getPassword()).thenReturn(JWT_SIGNED_SERIALIZED);
    when(user.isEnable()).thenReturn(true);
    when(user.getFieldCenter()).thenReturn(fieldCenter);
    when(fieldCenter.getAcronym()).thenReturn("RS");
    when(securityContextService.generateSecretKey()).thenReturn(sharedSecret);
    when(securityContextService.generateToken(authenticationData, sharedSecret)).thenReturn(TOKEN);
    assertTrue(securityServiceBean.authenticate(authenticationData) instanceof UserSecurityAuthorizationDto);
  }

  @Test(expected = AuthenticationException.class)
  public void method_authenticate_should_throw_AuthenticationException_when_password_and_key_notEquals()
    throws TokenException, AuthenticationException, DataNotFoundException {
    when(user.getPassword()).thenReturn(OTHER_PASSWORD);
    securityServiceBean.authenticate(authenticationData);
  }

  @Test(expected = AuthenticationException.class)
  public void method_authenticate_should_throw_AuthenticationException_when_user_not_enable()
    throws TokenException, AuthenticationException, DataNotFoundException {
    when(user.getPassword()).thenReturn(PASSWORD);
    when(user.isEnable()).thenReturn(NEGATIVE_ANSWER);
    securityServiceBean.authenticate(authenticationData);
  }

  @Test
  public void method_authenticate_should_evocate_getAcronym_of_getFielCenter_by_user()
    throws TokenException, AuthenticationException, DataNotFoundException {
    when(user.getPassword()).thenReturn(PASSWORD);
    when(user.isEnable()).thenReturn(POSITIVE_ANSWER);
    when(user.getFieldCenter()).thenReturn(fieldCenter);
    securityServiceBean.authenticate(authenticationData);
    verify(fieldCenter).getAcronym();
  }

  @Test
  public void method_projectAuthenticate_should_return_jwtSignedAndSerialized() throws Exception {
    byte[] sharedSecret = new byte[32];
    when(systemConfigDao.fetchSystemConfig()).thenReturn(systemConfig);
    when(systemConfig.getProjectToken()).thenReturn(PASSWORD);
    when(authenticationData.isValid()).thenReturn(true);
    when(authenticationData.getKey()).thenReturn(PASSWORD);
    when(user.getPassword()).thenReturn(JWT_SIGNED_SERIALIZED);
    when(user.isEnable()).thenReturn(true);
    when(user.getFieldCenter()).thenReturn(fieldCenter);
    when(fieldCenter.getAcronym()).thenReturn("RS");
    when(securityContextService.generateSecretKey()).thenReturn(sharedSecret);
    when(securityContextService.generateToken(authenticationData, sharedSecret)).thenReturn(JWT_SIGNED_SERIALIZED);
    assertEquals(JWT_SIGNED_SERIALIZED, securityServiceBean.projectAuthenticate(authenticationData));
  }

  @Test(expected = AuthenticationException.class)
  public void method_projectAuthenticate_should_throw_AuthenticationException_when_authenticationData_invalid()
    throws TokenException, AuthenticationException {
    when(authenticationData.isValid()).thenReturn(NEGATIVE_ANSWER);
    securityServiceBean.projectAuthenticate(authenticationData);
  }

  @Test(expected = AuthenticationException.class)
  public void method_projectAuthenticate_should_throw_AuthenticationException_when_fetchSystemConfig_of_systemConfigDao_throw_NoResultException()
    throws TokenException, AuthenticationException {
    when(systemConfigDao.fetchSystemConfig()).thenThrow(NoResultException.class);
    securityServiceBean.projectAuthenticate(authenticationData);
  }

  @Test(expected = AuthenticationException.class)
  public void method_projectAuthenticate_should_throw_AuthenticationException_when_getProjectToken_of_systemConfig_not_equal_password()
    throws TokenException, AuthenticationException {
    when(authenticationData.isValid()).thenReturn(POSITIVE_ANSWER);
    when(systemConfig.getProjectToken()).thenReturn(OTHER_PASSWORD);
    securityServiceBean.projectAuthenticate(authenticationData);
  }

  @Test
  public void method_invalidate_should_evocate_removeToken_of_securityContextService() {
    securityServiceBean.invalidate(TOKEN);
    verify(securityContextService).removeToken(TOKEN);
  }

  @Test
  @Ignore
  public void method_private_initializeToken_should_return_jwtSignedAndSerialize() throws Exception {
    secretKey = TOKEN.getBytes();
    when(securityContextService.generateSecretKey()).thenReturn(secretKey);
    when(securityContextService.generateToken(authenticationData, secretKey)).thenReturn(JWT_SIGNED_SERIALIZED);
    whenNew(SessionIdentifier.class).withArguments(JWT_SIGNED_SERIALIZED, secretKey, authenticationData)
      .thenReturn(sessionIdentifier);
    assertEquals(JWT_SIGNED_SERIALIZED, invokeMethod(securityServiceBean, "initializeToken", authenticationData));
    verifyNew(SessionIdentifier.class).withArguments(JWT_SIGNED_SERIALIZED, secretKey, authenticationData);
    verify(securityContextService).addSession(sessionIdentifier);
  }

  @Test
  public void method_validatePasswordReset_should_call_passwordResetContextService_hasToken() throws Exception {
    when(passwordResetContextService.hasToken(TOKEN)).thenReturn(true);
    securityServiceBean.validatePasswordReset(TOKEN);
  }

  @Test
  public void method_getPasswordResetToken_should_call_userDao_exists() throws Exception {
    when(userDao.exists(EMAIL)).thenReturn(true);
    PasswordResetRequestDto requestData = new PasswordResetRequestDto();
    requestData.setUserEmail(EMAIL);
    securityServiceBean.getPasswordResetToken(requestData);
    verify(userDao, times(1)).exists(EMAIL);
  }

  @Test
  public void method_getPasswordResetToken_should_call_passwordResetContextService_registerToken() throws Exception {
    PasswordResetRequestDto requestData = new PasswordResetRequestDto();
    requestData.setUserEmail(EMAIL);
    when(userDao.exists(EMAIL)).thenReturn(true);
    SecureRandom secureRandom = new SecureRandom();
    byte[] sharedSecret = new byte[32];
    secureRandom.nextBytes(sharedSecret);
    when(securityContextService.generateSecretKey()).thenReturn(sharedSecret);
    when(securityContextService.generateToken(requestData, sharedSecret)).thenReturn(TOKEN);
    assertEquals(TOKEN, securityServiceBean.getPasswordResetToken(requestData));
    verify(passwordResetContextService, times(1)).registerToken(requestData);
  }

  @Test
  public void method_getPasswordResetToken_should_return_TOKEN() throws Exception {
    PasswordResetRequestDto requestData = new PasswordResetRequestDto();
    requestData.setUserEmail(EMAIL);
    when(userDao.exists(EMAIL)).thenReturn(true);
    SecureRandom secureRandom = new SecureRandom();
    byte[] sharedSecret = new byte[32];
    secureRandom.nextBytes(sharedSecret);
    when(securityContextService.generateSecretKey()).thenReturn(sharedSecret);
    when(securityContextService.generateToken(requestData, sharedSecret)).thenReturn(TOKEN);
    assertEquals(TOKEN, securityServiceBean.getPasswordResetToken(requestData));
  }

  @Test(expected = DataNotFoundException.class)
  public void method_getPasswordResetToken_should_throw_DataNotFoundException() throws Exception {
    when(userDao.exists(EMAIL)).thenReturn(false);
    PasswordResetRequestDto requestData = new PasswordResetRequestDto();
    requestData.setUserEmail(EMAIL);
    securityServiceBean.getPasswordResetToken(requestData);
  }

  @Test
  public void method_getRequestEmail_should_call_passwordResetContextService_getRequestEmail() throws DataNotFoundException {
    when(passwordResetContextService.getRequestEmail(TOKEN)).thenReturn(EMAIL);
    securityServiceBean.getRequestEmail(TOKEN);
    verify(passwordResetContextService, times(1)).getRequestEmail(TOKEN);
  }

  @Test(expected = DataNotFoundException.class)
  public void method_getRequestEmail_should_throw_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(passwordResetContextService).getRequestEmail(TOKEN);
    securityServiceBean.getRequestEmail(TOKEN);
  }

  @Test
  public void method_removePasswordResetRequests_should_call_passwordResetContextService_removeRequests() {
    securityServiceBean.removePasswordResetRequests(EMAIL);
    verify(passwordResetContextService, times(1)).removeRequests(EMAIL);
  }

  @Test
  public void method_participantAuthenticate_should_return_ParticipantSecurityAuthorizationDto() throws DataNotFoundException, TokenException, AuthenticationException {
    byte[] sharedSecret = new byte[32];
    when(authenticationData.getUserEmail()).thenReturn(EMAIL);
    when(authenticationData.getKey()).thenReturn(PASSWORD);
    when(participantDao.fetchByEmail(EMAIL)).thenReturn(participant);
    when(participant.getPassword()).thenReturn(PASSWORD);
    when(securityContextService.generateSecretKey()).thenReturn(sharedSecret);
    when(securityContextService.generateToken(authenticationData, sharedSecret)).thenReturn(JWT_SIGNED_SERIALIZED);
    assertEquals(securityServiceBean.participantAuthenticate(authenticationData).getToken(), JWT_SIGNED_SERIALIZED);
    verify(participantDao, times(1)).addAuthToken(EMAIL, JWT_SIGNED_SERIALIZED);
  }

  @Test(expected = AuthenticationException.class)
  public void method_participantAuthenticate_should_throw_DataNotFoundException() throws DataNotFoundException, TokenException, AuthenticationException {
    when(participantDao.fetchByEmail(EMAIL)).thenThrow(new DataNotFoundException());
    securityServiceBean.participantAuthenticate(authenticationData);
  }

  @Test(expected = AuthenticationException.class)
  public void method_participantAuthenticate_should_throw_AuthenticationException() throws DataNotFoundException, TokenException, AuthenticationException {
    when(authenticationData.getUserEmail()).thenReturn(EMAIL);
    when(authenticationData.getKey()).thenReturn(PASSWORD);
    when(participantDao.fetchByEmail(EMAIL)).thenReturn(participant);
    when(participant.getPassword()).thenReturn("PASSWORD");
    securityServiceBean.participantAuthenticate(authenticationData);
  }

  @Test
  public void method_invalidateParticipantAuthenticate_should_call_removeAuthToken() {
    securityServiceBean.invalidateParticipantAuthenticate(EMAIL, TOKEN);
    verify(participantDao, times(1)).removeAuthToken(EMAIL, TOKEN);
  }

  @Test
  public void method_registerParticipantPasswordResetToken_should_evoke_token_registers() throws TokenException, DataNotFoundException {
    PasswordResetRequestDto requestData = spy(new PasswordResetRequestDto());
    requestData.setUserEmail("mock@email.com");
    when(participantDao.fetchByEmail(requestData.getEmail())).thenReturn(participant);
    securityServiceBean.registerParticipantPasswordResetToken(requestData);
    verify(requestData, times(1)).setToken(Mockito.anyString());
    verify(passwordResetContextService, times(1)).registerToken(requestData);
  }

  @Test(expected = DataNotFoundException.class)
  public void method_registerParticipantPasswordResetToken_should_catch_DataNotFoundException() throws TokenException, DataNotFoundException {
    PasswordResetRequestDto requestData = spy(new PasswordResetRequestDto());
    requestData.setUserEmail("mock@email.com");
    when(participantDao.fetchByEmail(requestData.getEmail())).thenThrow(DataNotFoundException.class);
    securityServiceBean.registerParticipantPasswordResetToken(requestData);
  }
}
