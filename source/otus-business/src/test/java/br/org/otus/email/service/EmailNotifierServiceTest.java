package br.org.otus.email.service;

import br.org.otus.communication.CommunicationDataBuilder;
import br.org.otus.communication.GenericCommunicationData;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.BasicEmailSender;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.user.dto.UserDto;
import br.org.owail.io.TemplateReader;
import br.org.owail.sender.email.Sender;
import br.org.owail.sender.gmail.GMailer;
import br.org.tutty.Equalizer;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EmailNotifierServiceBean.class, GMailer.class, CommunicationDataBuilder.class, Equalizer.class})
public class EmailNotifierServiceTest {
  private static String PASSWORD = "PASSWORD";
  private static String EMAIL = "EMAIL";

  @InjectMocks
  private EmailNotifierServiceBean service;

  @Mock
  private SystemConfigDaoBean systemConfigDao;
  @Mock
  private BasicEmailSender emailSender;
  @Mock
  private GMailer gmailer;
  @Mock
  private TemplateReader templateReader;
  @Mock
  private OtusInitializationConfigDto initializationData;
  @Mock
  private UserDto user;
  @Mock
  private GenericCommunicationData genericCommunicationData;
  @Mock
  private CommunicationGatewayService communicationGatewayService;

  @Before
  public void setup() throws Exception {
    when(initializationData.getUser()).thenReturn(user);
    when(user.getEmail()).thenReturn(EMAIL);

    whenNew(CommunicationGatewayService.class).withAnyArguments().thenReturn(communicationGatewayService);

    mockStatic(GMailer.class);
    when(GMailer.createTLSMailer()).thenReturn(gmailer);

    whenNew(TemplateReader.class).withNoArguments().thenReturn(templateReader);
  }

  @Test
  public void getSender_method_should_return_the_system_email_sender() throws EncryptedException, DataNotFoundException {
    when(systemConfigDao.findEmailSender()).thenReturn(emailSender);
    when(emailSender.getPassword()).thenReturn(PASSWORD);

    Object sender = service.getSender();

    assertThat(sender, instanceOf(Sender.class));
  }

  @Test
  public void sendSystemInstallationEmail_method_should_send_an_SystemInstallationEmail() throws Exception {
    mockStatic(CommunicationDataBuilder.class);
    mockStatic(Equalizer.class);
    when(CommunicationDataBuilder.systemInstallation(initializationData.getUser().getEmail())).thenReturn(genericCommunicationData);

    service.sendSystemInstallationEmail(initializationData);

    verify(communicationGatewayService).sendMail(genericCommunicationData.toJson());
  }

}
