package br.org.otus.email.user.signup;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.model.User;
import br.org.owail.sender.email.Recipient;
import br.org.owail.sender.email.Sender;

@RunWith(MockitoJUnitRunner.class)
public class NewUserNotificationEmailTest {
	private static final String EMAIL = "otus@otus.com";
	@InjectMocks
	NewUserNotificationEmail newUserNotificationEmail;
	@Mock
	Sender sender;
	@Mock
	Recipient recipient;
	@Mock
	User user;

	@Test
	public void methodo_DefineRecipient_should_evocate_addTORecipient() {
		newUserNotificationEmail = spy(new NewUserNotificationEmail(sender, recipient, user));
		newUserNotificationEmail.defineRecipient(EMAIL);
		verify(newUserNotificationEmail).addTORecipient("recipient", EMAIL);
	}
}
