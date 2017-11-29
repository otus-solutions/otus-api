package br.org.otus.email.user.management;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.model.User;

@RunWith(MockitoJUnitRunner.class)
public class EnableUserNotificationEmailTest {
	private static final String RECIPIENT_NAME = "recipient";
	@Mock
	User user;
	@Spy
	private EnableUserNotificationEmail enableUserNotificationEmail;

	@Test
	public void method_defineRecipient_should_evocate_addTORecipientMethod() {
		enableUserNotificationEmail.defineRecipient(user);
		verify(enableUserNotificationEmail).addTORecipient(RECIPIENT_NAME, user.getEmail());
	}
}
