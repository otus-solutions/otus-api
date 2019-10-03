package br.org.otus.user.signup;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.builder.SystemConfigBuilder;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.OtusEmailFactory;
import br.org.otus.email.dto.EmailSenderDto;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.email.user.signup.NewUserGreetingsEmail;
import br.org.otus.email.user.signup.NewUserNotificationEmail;
import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import br.org.otus.user.dto.SignupDataDto;
import br.org.otus.user.management.ManagementUserService;
import br.org.owail.sender.email.Recipient;
import br.org.owail.sender.email.Sender;
import br.org.tutty.Equalizer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SignupServiceBean.class, Equalizer.class, Recipient.class, OtusEmailFactory.class,
		SystemConfigBuilder.class })
public class SignupServiceBeanTest {
	private static final Boolean NEGATIVE_ANSWER = false;
	private static final Boolean POSITIVE_ANSWER = true;
	private static final String EMAIL = "otus@otus.com";
	private static final String NAME = "Otus";
	private static final String PASSWORD = "#12345";

	@InjectMocks
	private SignupServiceBean signupServiceBean = spy(new SignupServiceBean());
	@Mock
	private UserDao userDao;
	@Mock
	private EmailNotifierService emailNotifierService;
	@Mock
	private ManagementUserService managementUserService;
	@Mock
	private SignupDataDto signupDataDto;
	@Mock
	private OtusInitializationConfigDto initializationConfigDto;
	@Mock
	private NewUserGreetingsEmail greetingsEmail;
	@Mock
	private User userToRegister;
	@Mock
	private NewUserNotificationEmail notificationEmail;
	@Mock
	private Recipient recipientUser;
	@Mock
	private EmailSenderDto emailSenderDto;

	private User user;
	private User systemAdministrator;
	private Sender sender;

	@Before
	public void setUp() throws Exception {
		user = spy(new User());
		whenNew(User.class).withNoArguments().thenReturn(user);
	}
	//TODO refatorar este teste, quebrou na mudanca de versão do powerMock
	@Ignore // atualização do powerMock para 1.7.3
	@Test
	public void method_create_with_signupDataDtoValid_and_managementUserServiceUnique_should_verify_evocation_internal_methods()
			throws Exception {
		when(signupDataDto.isValid()).thenReturn(POSITIVE_ANSWER);
		when(managementUserService.isUnique(anyString())).thenReturn(POSITIVE_ANSWER);
		mockStatic(Equalizer.class);

		sender = spy(new Sender(NAME, EMAIL, PASSWORD));
		when(emailNotifierService.getSender()).thenReturn(sender);
		when(user.getName()).thenReturn(NAME);
		when(user.getEmail()).thenReturn(EMAIL);
		mockStatic(Recipient.class);

		systemAdministrator = spy(new User());
		when(userDao.findAdmin()).thenReturn(systemAdministrator);
		when(systemAdministrator.getName()).thenReturn(NAME);
		when(systemAdministrator.getEmail()).thenReturn(EMAIL);
		mockStatic(OtusEmailFactory.class);

		signupServiceBean.create(signupDataDto);
		verifyNew(User.class, times(2)).withNoArguments();
		verifyStatic(times(1));
		Equalizer.equalize(signupDataDto, user);
		verify(emailNotifierService).getSender();
		verifyPrivate(signupServiceBean).invoke("sendEmailToUser", user, sender);
		verifyPrivate(signupServiceBean).invoke("sendEmailToAdmin", sender, user);
		verify(userDao).persist(user);

	}

	@Test(expected = ValidationException.class)
	public void method_create_with_signupDataDtoInvalid_should_throw_ValidationException()
			throws EmailNotificationException, DataNotFoundException, AlreadyExistException, ValidationException,
			EncryptedException {
		when(signupDataDto.isValid()).thenReturn(NEGATIVE_ANSWER);
		signupServiceBean.create(signupDataDto);

	}

	@Test(expected = AlreadyExistException.class)
	public void method_create_with_signupDataDtoValid_and_managementUserServiceNotUnique_should_throw_AlreadyExistException()
			throws EmailNotificationException, DataNotFoundException, AlreadyExistException, ValidationException,
			EncryptedException {
		when(signupDataDto.isValid()).thenReturn(POSITIVE_ANSWER);
		when(managementUserService.isUnique(anyString())).thenReturn(NEGATIVE_ANSWER);
		signupServiceBean.create(signupDataDto);
	}

	@Test
	public void method_create_with_initializationConfigDtoValid_and_managementUserServiceUnique_should_invoke_internal_methods()
			throws Exception {
		when(initializationConfigDto.isValid()).thenReturn(POSITIVE_ANSWER);
		when(initializationConfigDto.getEmailSender()).thenReturn(emailSenderDto);
		when(emailSenderDto.getEmail()).thenReturn(EMAIL);
		when(managementUserService.isUnique(EMAIL)).thenReturn(POSITIVE_ANSWER);
		mockStatic(SystemConfigBuilder.class);
		when(SystemConfigBuilder.class, "buildInitialUser", initializationConfigDto).thenReturn(user);

		signupServiceBean.create(initializationConfigDto);
		verifyStatic(times(1));
		SystemConfigBuilder.buildInitialUser(initializationConfigDto);
		verify(emailNotifierService).sendSystemInstallationEmail(initializationConfigDto);
		verify(userDao).persist(user);

	}

	@Test(expected = ValidationException.class)
	public void method_create_with_InitializationConfigDtoInvalid_should_throw_ValidationException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
		when(initializationConfigDto.isValid()).thenReturn(NEGATIVE_ANSWER);
		signupServiceBean.create(initializationConfigDto);
	}

	@Test(expected = AlreadyExistException.class)
	public void method_create_with_initializationConfigDtoValid_and_managementUserServiceNotUnique__should_throw_AlreadyExistException()
			throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
		when(initializationConfigDto.isValid()).thenReturn(POSITIVE_ANSWER);
		when(initializationConfigDto.getEmailSender()).thenReturn(emailSenderDto);
		when(emailSenderDto.getEmail()).thenReturn(EMAIL);
		when(managementUserService.isUnique(EMAIL)).thenReturn(NEGATIVE_ANSWER);
		signupServiceBean.create(initializationConfigDto);
	}
}
