package br.org.otus.email.user.signup;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.owail.sender.email.Recipient;

@RunWith(MockitoJUnitRunner.class)
public class NewUserGreetingsEmailTest {
  private static final String EMAIL = "otus@otus.com";
  @Mock
  private Recipient recipient;
  private NewUserGreetingsEmail newUserGreetingsEmail;

  @Test
  public void method_defineRecipient_should_evocate_addTORecipientMethod() {
    newUserGreetingsEmail = spy(new NewUserGreetingsEmail(recipient));
    newUserGreetingsEmail.defineRecipient(EMAIL);
    verify(newUserGreetingsEmail).addTORecipient("recipient", EMAIL);
  }

}
