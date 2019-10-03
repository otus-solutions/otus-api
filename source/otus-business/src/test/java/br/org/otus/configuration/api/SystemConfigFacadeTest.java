package br.org.otus.configuration.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.configuration.service.SystemConfigService;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.response.exception.HttpResponseException;

@RunWith(PowerMockRunner.class)
public class SystemConfigFacadeTest {
	private static final String PROJECT_TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";

	private static final Boolean POSITIVE_ANSWER = true;

	@InjectMocks
	private SystemConfigFacade systemConfigFacade;
	@Mock
	private SystemConfigService systemConfigService;
	@Mock
	private EmailNotifierService emailNotifierService;
	@Mock
	private OtusInitializationConfigDto initializationConfigDto;
	@Mock
	private OtusInitializationConfigDto otusInitializationConfigDto;

	@Test
	public void method_initConfiguration_should_return_projectTokenString() {
		assertEquals(PROJECT_TOKEN, systemConfigFacade.initConfiguration(initializationConfigDto, PROJECT_TOKEN));
	}

	@Test(expected = HttpResponseException.class)
	public void method_initConfiguration_should_throw_HttpResponseException_if_caught_EmailNotificationException()
			throws EncryptedException, EmailNotificationException, AlreadyExistException {
		doThrow(new EmailNotificationException()).when(systemConfigService).initConfiguration(initializationConfigDto,
				PROJECT_TOKEN);
		systemConfigFacade.initConfiguration(initializationConfigDto, PROJECT_TOKEN);
	}

	@Test(expected = HttpResponseException.class)
	public void method_initConfiguration_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws EncryptedException, EmailNotificationException, AlreadyExistException {
		doThrow(new EncryptedException()).when(systemConfigService).initConfiguration(initializationConfigDto,
				PROJECT_TOKEN);
		systemConfigFacade.initConfiguration(initializationConfigDto, PROJECT_TOKEN);
	}

	@Test(expected = HttpResponseException.class)
	public void method_initConfiguration_should_throw_HttpResponseException_if_caught_AlreadyExistException()
			throws EncryptedException, EmailNotificationException, AlreadyExistException {
		doThrow(new AlreadyExistException()).when(systemConfigService).initConfiguration(initializationConfigDto,
				PROJECT_TOKEN);
		systemConfigFacade.initConfiguration(initializationConfigDto, PROJECT_TOKEN);
	}

	@Test
	public void method_validateEmailService_should_check_evocation_of_verifyEmailServiceMethod_by_systemConfigService()
			throws EmailNotificationException, EncryptedException {
		systemConfigFacade.validateEmailService(otusInitializationConfigDto);
		verify(systemConfigService).verifyEmailService(otusInitializationConfigDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_validateEmailService_should_throw_HttpResponseException_if_caught_EmailNotificationException()
			throws EmailNotificationException, EncryptedException {
		doThrow(new EmailNotificationException()).when(systemConfigService)
				.verifyEmailService(otusInitializationConfigDto);
		systemConfigFacade.validateEmailService(otusInitializationConfigDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_validateEmailService_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws EmailNotificationException, EncryptedException {
		doThrow(new EncryptedException()).when(systemConfigService).verifyEmailService(otusInitializationConfigDto);
		systemConfigFacade.validateEmailService(otusInitializationConfigDto);
	}

	@Test
	public void method_isReady_should_return_positive_answer() {
		when(systemConfigService.isReady()).thenReturn(POSITIVE_ANSWER);
		assertTrue(systemConfigFacade.isReady());
	}

	@Test
	public void method_buildToken_should_return_tokenString() {
		when(systemConfigService.buildToken()).thenReturn(PROJECT_TOKEN);
		assertEquals(PROJECT_TOKEN, systemConfigFacade.buildToken());
	}

}