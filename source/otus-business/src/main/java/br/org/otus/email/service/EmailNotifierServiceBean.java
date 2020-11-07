package br.org.otus.email.service;

import br.org.otus.communication.CommunicationDataBuilder;
import br.org.otus.communication.GenericCommunicationData;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.BasicEmailSender;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.security.EncryptorResources;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.user.signup.SignupService;
import br.org.owail.sender.email.Sender;
import br.org.tutty.Equalizer;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.net.MalformedURLException;
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
  public Sender getSender() throws EncryptedException, DataNotFoundException {
    try {
      BasicEmailSender emailSender = systemConfigDao.findEmailSender();
      return new Sender(emailSender.getName(), emailSender.getEmail(), EncryptorResources.decrypt(emailSender.getPassword()));

    } catch (NoResultException e) {
      throw new DataNotFoundException();
    }
  }

}
