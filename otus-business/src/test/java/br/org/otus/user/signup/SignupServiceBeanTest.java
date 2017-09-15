package br.org.otus.user.signup;

import static org.mockito.Matchers.anyString;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.email.OtusEmailFactory;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.email.user.signup.NewUserGreetingsEmail;
import br.org.otus.email.user.signup.NewUserNotificationEmail;
import br.org.otus.model.User;
import br.org.otus.user.UserDao;
import br.org.otus.user.dto.SignupDataDto;
import br.org.otus.user.management.ManagementUserService;
import br.org.owail.sender.email.Recipient;
import br.org.owail.sender.email.Sender;
import br.org.tutty.Equalizer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SignupServiceBean.class, Equalizer.class, Recipient.class, OtusEmailFactory.class })
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

	private Sender sender;
	@Mock
	private NewUserGreetingsEmail greetingsEmail;
	@Mock
	private User userToRegister;
	@Mock
	private NewUserNotificationEmail notificationEmail;
	@Mock
	private Recipient recipientUser;

	private User user;
	private User systemAdministrator;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void method_create_with_signupDataDto_should_verify_evocation_internal_methods() throws Exception {
		when(signupDataDto.isValid()).thenReturn(POSITIVE_ANSWER);
		when(managementUserService.isUnique(anyString())).thenReturn(POSITIVE_ANSWER);

		user = spy(new User());
		whenNew(User.class).withNoArguments().thenReturn(user);
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
		verifyNew(User.class, Mockito.times(2)).withNoArguments();
		verifyStatic(Mockito.times(1));
		Equalizer.equalize(signupDataDto, user);
		verify(emailNotifierService).getSender();
		verifyPrivate(signupServiceBean).invoke("sendEmailToUser", user, sender);
		verifyPrivate(signupServiceBean).invoke("sendEmailToAdmin", sender, user);
		verify(userDao).persist(user);

	}

	@Test(expected = ValidationException.class)
	public void method_SignupDataDto_should_throw_ValidationException() throws EmailNotificationException,
			DataNotFoundException, AlreadyExistException, ValidationException, EncryptedException {
		when(signupDataDto.isValid()).thenReturn(NEGATIVE_ANSWER);
		signupServiceBean.create(signupDataDto);

	}

	@Test(expected = AlreadyExistException.class)
	public void method_SignupDataDto_should_throw_AlreadyExistException() throws EmailNotificationException,
			DataNotFoundException, AlreadyExistException, ValidationException, EncryptedException {
		when(signupDataDto.isValid()).thenReturn(POSITIVE_ANSWER);
		when(managementUserService.isUnique(Mockito.anyString())).thenReturn(NEGATIVE_ANSWER);
		signupServiceBean.create(signupDataDto);
	}

	@Test
	public void method_create_with_OtusInitializationConfigDto() {
	}

}
