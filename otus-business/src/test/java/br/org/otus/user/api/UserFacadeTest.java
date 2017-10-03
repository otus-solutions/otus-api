package br.org.otus.user.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.SignupDataDto;
import br.org.otus.user.management.ManagementUserService;
import br.org.otus.user.signup.SignupService;

@RunWith(PowerMockRunner.class)
public class UserFacadeTest {
	private static final String EMAIL = "otus@otus.com";
	@InjectMocks
	private UserFacade userFacade;
	@Mock
	private EmailNotifierService emailNotifierService;
	@Mock
	private ManagementUserService managementUserService;
	@Mock
	private SignupService signupService;
	@Mock
	private OtusInitializationConfigDto initializationConfigDto;
	@Mock
	private SignupDataDto signupDataDto;
	@Mock
	private ManagementUserDto managementUserDto;
	@Mock
	private User user;
	private List<ManagementUserDto> managementUserDtos;

	@Test
	public void method_create_with_OtusInitializationConfigDto_should_check_evocation_of_createMethod_by_signupService()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
		userFacade.create(initializationConfigDto);
		verify(signupService).create(initializationConfigDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_OtusInitializationConfigDto_should_throw_HttpResponseException_if_caught_EmailNotificationException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
		doThrow(new EmailNotificationException()).when(signupService).create(initializationConfigDto);
		userFacade.create(initializationConfigDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_OtusInitializationConfigDto_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
		doThrow(new EncryptedException()).when(signupService).create(initializationConfigDto);
		userFacade.create(initializationConfigDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_OtusInitializationConfigDto_should_throw_HttpResponseException_if_caught_AlreadyExistException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
		doThrow(new AlreadyExistException()).when(signupService).create(initializationConfigDto);
		userFacade.create(initializationConfigDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_OtusInitializationConfigDto_should_throw_HttpResponseException_if_caught_ValidationException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
		doThrow(new ValidationException()).when(signupService).create(initializationConfigDto);
		userFacade.create(initializationConfigDto);

	}

	@Test
	public void method_create_with_SignupDataDto_should_check_evocation_of_createMethod_by_signupService()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException,
			DataNotFoundException {
		userFacade.create(signupDataDto);
		verify(signupService).create(signupDataDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_SignupDataDto_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException,
			DataNotFoundException {
		doThrow(new EncryptedException()).when(signupService).create(signupDataDto);
		userFacade.create(signupDataDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_SignupDataDto_should_throw_HttpResponseException_if_caught_EmailNotificationException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException,
			DataNotFoundException {
		doThrow(new EmailNotificationException()).when(signupService).create(signupDataDto);
		userFacade.create(signupDataDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_SignupDataDto_should_throw_HttpResponseException_if_caught_AlreadyExistException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException,
			DataNotFoundException {
		doThrow(new AlreadyExistException()).when(signupService).create(signupDataDto);
		userFacade.create(signupDataDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_SignupDataDto_should_throw_HttpResponseException_if_caught_DataNotFoundException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException,
			DataNotFoundException {
		doThrow(new DataNotFoundException()).when(signupService).create(signupDataDto);
		userFacade.create(signupDataDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_with_SignupDataDto_should_throw_HttpResponseException_if_caught_ValidationException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException,
			DataNotFoundException {
		doThrow(new ValidationException()).when(signupService).create(signupDataDto);
		userFacade.create(signupDataDto);
	}

	@Test
	public void method_list_should_return_managementUserDtosList() {
		managementUserDtos = new ArrayList<>();
		managementUserDtos.add(managementUserDto);
		when(managementUserService.list()).thenReturn(managementUserDtos);
		assertEquals(managementUserDto, userFacade.list().get(0));
	}

	@Test
	public void method_disable_should_check_evocation_of_disableMethod_by_managementUserService()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		userFacade.disable(managementUserDto);
		verify(managementUserService).disable(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_disable_should_throw_HttpResponseException_if_caught_EmailNotificationException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new EmailNotificationException()).when(managementUserService).disable(managementUserDto);
		userFacade.disable(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_disable_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new EncryptedException()).when(managementUserService).disable(managementUserDto);
		userFacade.disable(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_disable_should_throw_HttpResponseException_if_caught_ValidationException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new ValidationException()).when(managementUserService).disable(managementUserDto);
		userFacade.disable(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_disable_should_throw_HttpResponseException_if_caught_DataNotFoundException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new DataNotFoundException()).when(managementUserService).disable(managementUserDto);
		userFacade.disable(managementUserDto);
	}

	@Test
	public void method_enable_should_check_evocation_of_enableMethod_by_managementUserService()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		userFacade.enable(managementUserDto);
		verify(managementUserService).enable(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_enable_should_throw_HttpResponseException_if_caught_EmailNotificationException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new EmailNotificationException()).when(managementUserService).enable(managementUserDto);
		userFacade.enable(managementUserDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_enable_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new EncryptedException()).when(managementUserService).enable(managementUserDto);
		userFacade.enable(managementUserDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_enable_should_throw_HttpResponseException_if_caught_ValidationException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new ValidationException()).when(managementUserService).enable(managementUserDto);
		userFacade.enable(managementUserDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_enable_should_throw_HttpResponseException_if_caught_DataNotFoundException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new DataNotFoundException()).when(managementUserService).enable(managementUserDto);
		userFacade.enable(managementUserDto);

	}

	@Test
	public void method_disableExtraction_should_check_evocation_of_disableMethod_by_managementUserService()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		userFacade.disableExtraction(managementUserDto);
		verify(managementUserService).disableExtraction(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_disableExtraction_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new EncryptedException()).when(managementUserService).disableExtraction(managementUserDto);
		userFacade.disableExtraction(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_disableExtraction_should_throw_HttpResponseException_if_caught_ValidationException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new ValidationException()).when(managementUserService).disableExtraction(managementUserDto);
		userFacade.disableExtraction(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_disableExtraction_should_throw_HttpResponseException_if_caught_DataNotFoundException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new DataNotFoundException()).when(managementUserService).disableExtraction(managementUserDto);
		userFacade.disableExtraction(managementUserDto);
	}

	@Test
	public void method_enableExtraction_should_check_evocation_of_enableMethod_by_managementUserService()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		userFacade.enableExtraction(managementUserDto);
		verify(managementUserService).enableExtraction(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_enableExtraction_should_throw_HttpResponseException_if_caught_EncryptedException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new EncryptedException()).when(managementUserService).enableExtraction(managementUserDto);
		userFacade.enableExtraction(managementUserDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_enableExtraction_should_throw_HttpResponseException_if_caught_ValidationException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new ValidationException()).when(managementUserService).enableExtraction(managementUserDto);
		userFacade.enableExtraction(managementUserDto);

	}

	@Test(expected = HttpResponseException.class)
	public void method_enableExtraction_should_throw_HttpResponseException_if_caught_DataNotFoundException()
			throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
		doThrow(new DataNotFoundException()).when(managementUserService).enableExtraction(managementUserDto);
		userFacade.enableExtraction(managementUserDto);

	}


	@Test
	public void method_updateFieldCenter_should_check_evocation_of_() throws DataNotFoundException {
		userFacade.updateFieldCenter(managementUserDto);
		verify(managementUserService).updateFieldCenter(managementUserDto);
	}

	@Test(expected = HttpResponseException.class)
	public void method_updateFieldCenter_should_throw_HttpResponseException_if_caught_DataNotFoundException()
			throws DataNotFoundException {
		doThrow(new DataNotFoundException()).when(managementUserService).updateFieldCenter(managementUserDto);
		userFacade.updateFieldCenter(managementUserDto);

	}

	@Test
	public void method_fetchByEmail_should_return_user() throws DataNotFoundException {
		when(managementUserService.fetchByEmail(EMAIL)).thenReturn(user);
		assertEquals(user, userFacade.fetchByEmail(EMAIL));
	}
	
	@Test(expected = HttpResponseException.class)
	public void method_fetchByEmail_should_throw_HttpResponseException_if_caught_DataNotFoundException() throws DataNotFoundException {
		doThrow(new DataNotFoundException()).when(managementUserService).fetchByEmail(EMAIL);
		userFacade.fetchByEmail(EMAIL);
	}
	
	

}
