package br.org.otus.email.service;

import br.org.otus.communication.CommunicationDataBuilder;
import br.org.otus.communication.GenericCommunicationData;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.BasicEmailSender;
import br.org.otus.email.OtusEmail;
import br.org.otus.email.OtusEmailFactory;
import br.org.otus.email.system.SystemInstallationEmail;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.security.EncryptorResources;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.user.signup.SignupService;
import br.org.owail.io.TemplateReader;
import br.org.owail.sender.email.EmailCompositionException;
import br.org.owail.sender.email.Recipient;
import br.org.owail.sender.email.Sender;
import br.org.owail.sender.gmail.GMailer;
import br.org.tutty.Equalizer;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.logging.Logger;

@Stateless
public class EmailNotifierServiceBean implements EmailNotifierService {

  @Inject
  private SystemConfigDaoBean systemConfigDao;

  private final static Logger LOGGER = Logger.getLogger(SignupService.class.getName());

  @Override
  public void sendSystemInstallationEmail(OtusInitializationConfigDto initializationData) throws EmailNotificationException, EncryptedException {
    BasicEmailSender emailSenderDto = new BasicEmailSender();
    Equalizer.equalize(initializationData.getEmailSender(), emailSenderDto);

    String recipientEmail = initializationData.getUser().getEmail();

    GenericCommunicationData genericCommunicationData = CommunicationDataBuilder.systemInstallation(recipientEmail);
    try {
      CommunicationGatewayService emailSender = new CommunicationGatewayService();
      emailSender.sendMail(genericCommunicationData.toJson());

    } catch (ReadRequestException | MalformedURLException ex) {
      LOGGER.severe("sendSystemInstallationEmail: " + recipientEmail);
    }
  }



  @Override
  @Asynchronous
  public void sendEmail(OtusEmail email) throws EmailNotificationException {
    sendEmailSync(email);
  }

  @Override
  public void sendEmailSync(OtusEmail email) throws EmailNotificationException {
    GMailer mailer = GMailer.createTLSMailer();

    mailer.setFrom(email.getFrom());
    mailer.addRecipients(email.getRecipients());
    mailer.setSubject(email.getSubject());
    mailer.setContentType(email.getContentType());
    mailer.setContent(mergeTemplate(email.getContentDataMap(), email.getTemplatePath()));

    try {
      mailer.send();
    } catch (MessagingException | EmailCompositionException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Sender getSender() throws EncryptedException, DataNotFoundException {
    try {
      BasicEmailSender emailSender = systemConfigDao.findEmailSender();
      return new Sender(emailSender.getName(), emailSender.getEmail(), EncryptorResources.decrypt(emailSender.getPassword()));

    } catch (NoResultException e) {
      throw new DataNotFoundException();
    }
  }

  private String mergeTemplate(Map<String, String> dataMap, String template) {
    TemplateReader templateReader = new TemplateReader();
    String templateContent = templateReader.getFileToString(getClass().getClassLoader(), template);
    return templateReader.fillTemplate(dataMap, templateContent);
  }

}
