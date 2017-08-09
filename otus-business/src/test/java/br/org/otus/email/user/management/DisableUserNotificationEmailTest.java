package br.org.otus.email.user.management;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.JsonObject;

import br.org.otus.model.User;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DisableUserNotificationEmail.class)
public class DisableUserNotificationEmailTest {
	private static final String EMAIL_PROPERTY = "email";
	private static final String EMAIL_VALUE = "otus@otus.com";
	private static final String RECIPIENT_NAME = "recipient";

	private JsonObject userString;
	private User user;
	private DisableUserNotificationEmail disableUserNotificationEmailSpy;

	@Test
	public void method_DefineRecipient_should_verify_call_addToRecipient() {
		userString = new JsonObject();
		userString.addProperty(EMAIL_PROPERTY, EMAIL_VALUE);
		user = User.deserialize(userString.toString());
		disableUserNotificationEmailSpy = spy(new DisableUserNotificationEmail());
		disableUserNotificationEmailSpy.defineRecipient(user);

		verify(disableUserNotificationEmailSpy).addTORecipient(RECIPIENT_NAME, user.getEmail());
	}

}
