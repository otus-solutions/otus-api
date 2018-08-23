package br.org.otus.email.service;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.mail.MessagingException;

import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.BasicEmailSender;
import br.org.otus.email.OtusEmailFactory;
import br.org.otus.email.system.SystemInstallationEmail;
import br.org.otus.security.EncryptorResources;
import br.org.otus.user.dto.UserDto;
import br.org.owail.io.TemplateReader;
import br.org.owail.sender.email.EmailCompositionException;
import br.org.owail.sender.email.Recipient;
import br.org.owail.sender.email.Sender;
import br.org.owail.sender.gmail.GMailer;
import br.org.tutty.Equalizer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EmailNotifierServiceBean.class, Equalizer.class, Recipient.class, BasicEmailSender.class,
    Sender.class, EncryptorResources.class, OtusEmailFactory.class, TemplateReader.class, GMailer.class })

public class EmailNotifierServiceBeanTest {

  private static final String USER_NAME = "otus";
  private static final String USER_EMAIL = "otus@otus.com.br";
  private static final String USER_PASSWORD = "123456";
  private static final String DECRYPT_PASSWORD = "654321";

  @InjectMocks
  private EmailNotifierServiceBean emailNotifierServiceBean = PowerMockito.spy(new EmailNotifierServiceBean());
  @Mock
  private OtusInitializationConfigDto initializationData;
  @Mock
  private UserDto userDto;
  @Mock
  private BasicEmailSender emailSenderDto;
  @Mock
  private Sender sender;
  @Mock
  private SystemInstallationEmail email;
  @Mock
  private Recipient recipient;
  @Mock
  private TemplateReader templateReader;
  @Mock
  private GMailer mailer; 

  @Before
  public void setUp() throws Exception {
    mockStatic(TemplateReader.class);
    whenNew(TemplateReader.class).withNoArguments().thenReturn(templateReader);
    mockStatic(GMailer.class);
    when(GMailer.createTLSMailer()).thenReturn(mailer);
  }

  @Test
  public void sendSystemInstallationEmailMethod_should_invoke_sendEmailMethod() throws Exception {
    mockStatic(BasicEmailSender.class);
    whenNew(BasicEmailSender.class).withAnyArguments().thenReturn(emailSenderDto);
    mockStatic(Equalizer.class);

    when(initializationData.getUser()).thenReturn(userDto);
    when(userDto.getName()).thenReturn(USER_NAME);
    when(userDto.getEmail()).thenReturn(USER_EMAIL);

    when(emailSenderDto.getName()).thenReturn(USER_NAME);
    when(emailSenderDto.getEmail()).thenReturn(USER_EMAIL);
    when(emailSenderDto.getPassword()).thenReturn(USER_PASSWORD);
    mockStatic(Recipient.class);
    when(Recipient.createTO(USER_NAME, USER_EMAIL)).thenReturn(recipient);

    mockStatic(EncryptorResources.class);
    when(EncryptorResources.decrypt(USER_PASSWORD)).thenReturn(DECRYPT_PASSWORD);
    mockStatic(Sender.class);
    whenNew(Sender.class).withArguments(USER_NAME, USER_EMAIL, DECRYPT_PASSWORD).thenReturn(sender);
    mockStatic(OtusEmailFactory.class);
    when(OtusEmailFactory.createSystemInstallationEmail(sender, recipient)).thenReturn(email);

    emailNotifierServiceBean.sendSystemInstallationEmail(initializationData);
    verify(emailNotifierServiceBean, Mockito.times(1)).sendEmail(email);
  }

  @Test
  public void sendEmailMethod_should_invoke_sendEmailSync()
      throws EmailNotificationException, EmailCompositionException, MessagingException {
    emailNotifierServiceBean.sendEmail(email);
    verify(emailNotifierServiceBean, Mockito.times(1)).sendEmailSync(email);
  }

  @Test
  public void sendEmailSyncMethod_should_invoke_send_of_mailer()
      throws EmailNotificationException, EmailCompositionException, MessagingException {
    emailNotifierServiceBean.sendEmailSync(email);
    Mockito.verify(mailer, Mockito.times(1)).send();
  }

  @Test(expected = EmailNotificationException.class)
  public void sendEmailSyncMethod__should_capture_MessagingException() throws Exception {
    PowerMockito.doThrow(new MessagingException()).when(mailer, "send");
    emailNotifierServiceBean.sendEmailSync(email);
  }

  @Test(expected = EmailNotificationException.class)
  public void sendEmailSyncMethod__should_capture_EmailCompositionException() throws Exception {
    PowerMockito.doThrow(new EmailCompositionException()).when(mailer, "send");
    emailNotifierServiceBean.sendEmailSync(email);
  }
}
