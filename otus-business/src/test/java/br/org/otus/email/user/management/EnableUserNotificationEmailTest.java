package br.org.otus.email.user.management;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.email.service.EmailNotifierServiceBean;
import br.org.otus.model.User;

@RunWith(PowerMockRunner.class)
public class EnableUserNotificationEmailTest {	
	@Mock
	User user;	
	private EnableUserNotificationEmail enableUserNotificationEmail;

	@Test
	public void method_defineRecipient_should_evocate_addTORecipientMethod() {		
		enableUserNotificationEmail = spy(new EnableUserNotificationEmail());		
		enableUserNotificationEmail.defineRecipient(user);
		verify(enableUserNotificationEmail).addTORecipient("recipient", user.getEmail());		
	}

}
