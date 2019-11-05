package br.org.otus.configuration.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.builder.SystemConfigBuilder;
import br.org.otus.configuration.builder.TokenBuilder;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.system.SystemConfig;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.user.api.UserFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SystemConfigBuilder.class, TokenBuilder.class})
public class SystemConfigServiceBeanTest {
	private static final String PROJECT_TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";

	
	@InjectMocks
	private SystemConfigServiceBean systemConfigServiceBean = PowerMockito.spy(new SystemConfigServiceBean());
	@Mock
	private OtusInitializationConfigDto initializationConfigDto;
	@Mock
	private SystemConfigDaoBean systemConfigDao;
	@Mock
	private UserFacade userFacade;
	@Mock
	private EmailNotifierService emailNotifierService;
	@Mock
	private SystemConfig systemConfig;
	

	@Before
	public void setUp() {
	}

	@Test
	public void method_InitConfiguration_should_check_evocation_of_persistBySystemConfigDao_and_createByUserFacade() throws EncryptedException, EmailNotificationException, AlreadyExistException {
		when(systemConfigDao.isReady()).thenReturn(Boolean.FALSE);
		mockStatic(SystemConfigBuilder.class);
		when(SystemConfigBuilder.builSystemConfig(initializationConfigDto, PROJECT_TOKEN )).thenReturn(systemConfig);
		systemConfigServiceBean.initConfiguration(initializationConfigDto, PROJECT_TOKEN);
		verify(systemConfigDao).persist(systemConfig);
		verify(userFacade).create(initializationConfigDto);		
	}
	
	@Test(expected = AlreadyExistException.class)
	public void method_initConfiguration_should_throw_AlreadyExistException_when_isReady_valid() throws EncryptedException, EmailNotificationException, AlreadyExistException {
		when(systemConfigDao.isReady()).thenReturn(Boolean.TRUE);
		systemConfigServiceBean.initConfiguration(initializationConfigDto, PROJECT_TOKEN);				
	}

	@Test
	public void method_verifyEmailService_check_evocation_of_sendSystemInstallationEmail_by_emailNotifierService() throws EmailNotificationException, EncryptedException {
		systemConfigServiceBean.verifyEmailService(initializationConfigDto);
		verify(emailNotifierService).sendSystemInstallationEmail(initializationConfigDto);
	}

	@Test
	public void method_buildToken() {
		mockStatic(TokenBuilder.class);
		when(TokenBuilder.build()).thenReturn(PROJECT_TOKEN);
		assertEquals(PROJECT_TOKEN, systemConfigServiceBean.buildToken());
		
	}

	

}