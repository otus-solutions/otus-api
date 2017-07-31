package br.org.otus.email.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.mail.MessagingException;

import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.BasicEmailSender;
import br.org.otus.email.OtusEmailFactory;
import br.org.otus.user.dto.UserDto;
import br.org.owail.io.TemplateReader;
import br.org.owail.sender.email.EmailCompositionException;
import br.org.owail.sender.email.Recipient;
import br.org.owail.sender.gmail.GMailer;
import br.org.tutty.Equalizer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EmailNotifierServiceBean.class, Equalizer.class, Recipient.class, OtusEmailFactory.class,	GMailer.class })
public class EmailNotifierServiceBeanTest {	
	@Mock
	private TemplateReader templateReader;	
	@Mock
	private GMailer mailer;
	private OtusInitializationConfigDto initializationData;
	private BasicEmailSender emailSenderDto;
	private EmailNotifierServiceBean emailNotifierServiceBean;
	private UserDto user;

	@Before
	public void setUp() throws Exception {

		user = new UserDto();
		user.setName("OtusTestUser");
		user.setEmail("otusTestUser@otus.com");
		emailSenderDto = new BasicEmailSender("Otus Local", "ccem.projects@gmail.com", "cmFwYWR1cmExMDAyMDAzMDA=");
		initializationData = new OtusInitializationConfigDto();
		initializationData.setUser(user);
		emailNotifierServiceBean = spy(new EmailNotifierServiceBean());
		whenNew(BasicEmailSender.class).withNoArguments().thenReturn(emailSenderDto);		
		
		mockStatic(Equalizer.class);
		doNothing().when(Equalizer.class, "equalize", anyObject(), anyObject());
		whenNew(TemplateReader.class).withNoArguments().thenReturn(templateReader);		
		
		mockStatic(GMailer.class);		
		when(GMailer.class, "createTLSMailer").thenReturn(mailer);
	}
	@Test
	public void method_sendSystemInstallationEmail_should_evocate_sendEmailMethod() throws Exception {
		emailNotifierServiceBean.sendSystemInstallationEmail(initializationData);
		verify(emailNotifierServiceBean).sendEmail(anyObject());
	}	
	@Test(expected= EmailNotificationException.class)
	public void method_sendEmailSync_should_throw_MessagingException() throws Exception{
		Mockito.doThrow(MessagingException.class).when(mailer).send();
		emailNotifierServiceBean.sendSystemInstallationEmail(initializationData);		
	}	
	@Test(expected= EmailNotificationException.class)
	public void method_sendEmailSync_should_throw_EmailCompositionException() throws Exception{
		Mockito.doThrow(EmailCompositionException.class).when(mailer).send();
		emailNotifierServiceBean.sendSystemInstallationEmail(initializationData);		
	}
	@Test
	public void method_sendSystemInstallationEmail_should_evocate_SendEmailSyncMethod()
			throws EmailNotificationException, EncryptedException {
		emailNotifierServiceBean.sendSystemInstallationEmail(initializationData);
		verify(emailNotifierServiceBean).sendEmailSync(anyObject());
	}	

}
