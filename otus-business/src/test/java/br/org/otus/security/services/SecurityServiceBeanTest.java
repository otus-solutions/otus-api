package br.org.otus.security.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.invokeMethod;

import javax.persistence.NoResultException;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.model.User;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
import br.org.otus.system.SystemConfig;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.user.UserDao;

@RunWith(PowerMockRunner.class)
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
		when(user.getPassword()).thenReturn(PASSWORD);
		when(user.isEnable()).thenReturn(POSITIVE_ANSWER);
		whenNew(UserSecurityAuthorizationDto.class).withAnyArguments().thenReturn(userSecurityAuthorizationDto);
		assertTrue(securityServiceBean.authenticate(authenticationData) instanceof UserSecurityAuthorizationDto);
		verifyNew(UserSecurityAuthorizationDto.class).withNoArguments();
		verifyPrivate(securityServiceBean).invoke("initializeToken", authenticationData);
		verify(userSecurityAuthorizationDto).setToken(anyString());

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
		when(authenticationData.isValid()).thenReturn(POSITIVE_ANSWER);
		when(systemConfig.getProjectToken()).thenReturn(PASSWORD);
		doReturn(JWT_SIGNED_SERIALIZED).when(securityServiceBean, "initializeToken", authenticationData);
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

}
