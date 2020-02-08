package br.org.otus.email.user.management;

import br.org.owail.sender.email.Email;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetEmailTest {
  private static final String EMAIL = "otus@otus.com";
  private static final String HOSTPATH = "https://localhost:8080/otus";
  private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private final String SUBJECT = "[RECUPERAÇÃO DE ACESSO]";

  @InjectMocks
  private PasswordResetEmail passwordResetEmail;


  @Test
  public void method_PasswordResetEmail_should_construct_the_passwordResetEmail_object() {
    passwordResetEmail = new PasswordResetEmail(TOKEN, HOSTPATH);
    Map<String, String> dataMap = passwordResetEmail.getContentDataMap();
    assertEquals(TOKEN, dataMap.get("token"));
    assertEquals(HOSTPATH, dataMap.get("host"));
    assertEquals(SUBJECT, passwordResetEmail.getSubject());
  }

  @Test
  public void method_defineRecipient_should_call_addTORecipient() {
    passwordResetEmail = new PasswordResetEmail(TOKEN, HOSTPATH);
    passwordResetEmail.defineRecipient(EMAIL);
    assertEquals(EMAIL, passwordResetEmail.getRecipients().get(0).getEmailAddress());
  }

}
