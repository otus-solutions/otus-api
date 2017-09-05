package br.org.otus.security.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.model.User;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
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
	private static final String ACRONYM = "RS";
	private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";

	@InjectMocks
	private SecurityServiceBean securityServiceBean;
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
	private UserSecurityAuthorizationDto userSecurityAuthorizationDto;
	
	 
	@Before
	public void setUp() throws Exception  {
		when(authenticationData.getUserEmail()).thenReturn(EMAIL);
		when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
		userSecurityAuthorizationDto = PowerMockito.spy(new UserSecurityAuthorizationDto());
		
	}

	@Test
	public void method_authenticate_should_return_UserSecurityAuthorizationDto() throws TokenException, AuthenticationException, DataNotFoundException {
		when(authenticationData.getKey()).thenReturn(PASSWORD);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(user.isEnable()).thenReturn(POSITIVE_ANSWER);		
		assertTrue(securityServiceBean.authenticate(authenticationData) instanceof UserSecurityAuthorizationDto);
		//Mockito.verify(userSecurityAuthorizationDto).setToken(TOKEN);
		
	}
	
	@Test(expected = AuthenticationException.class)
	public void method_authenticate_should_throw_AuthenticationException_when_password_and_key_notEquals() throws TokenException, AuthenticationException, DataNotFoundException {
		when(authenticationData.getKey()).thenReturn(PASSWORD);
		when(user.getPassword()).thenReturn(OTHER_PASSWORD);				
		securityServiceBean.authenticate(authenticationData);
	}
	
	@Test(expected = AuthenticationException.class)
	public void method_authenticate_should_throw_AuthenticationException_when_user_not_enable() throws TokenException, AuthenticationException, DataNotFoundException {
		when(authenticationData.getKey()).thenReturn(PASSWORD);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(user.isEnable()).thenReturn(NEGATIVE_ANSWER);		
		securityServiceBean.authenticate(authenticationData);
	}

	@Test
	public void method_authenticate_should_evocate_getAcronym_of_getFielCenter_by_user() throws TokenException, AuthenticationException, DataNotFoundException {
		when(authenticationData.getKey()).thenReturn(PASSWORD);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(user.isEnable()).thenReturn(POSITIVE_ANSWER);
		when(user.getFieldCenter()).thenReturn(fieldCenter);
		securityServiceBean.authenticate(authenticationData);
		verify(fieldCenter).getAcronym();
	}
	
	
	

	@Test
	public void method_projectAuthenticate() {
	}

	@Test
	public void method_invalidate() {
		securityServiceBean.invalidate(TOKEN);
		verify(securityContextService).removeToken(TOKEN);
	}

}
