package br.org.otus.user.signup;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.otus.communication.CommunicationDataBuilder;
import br.org.otus.communication.GenericCommunicationData;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.exception.ReadRequestException;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.configuration.builder.SystemConfigBuilder;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.OtusEmailFactory;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.email.user.signup.NewUserNotificationEmail;
import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import br.org.otus.user.dto.SignupDataDto;
import br.org.otus.user.management.ManagementUserService;
import br.org.owail.sender.email.Recipient;
import br.org.owail.sender.email.Sender;
import br.org.tutty.Equalizer;

import java.net.MalformedURLException;

@Stateless
public class SignupServiceBean implements SignupService {

  @Inject
  private UserDao userDao;

  @Inject
  private EmailNotifierService emailNotifierService;

  @Inject
  private ManagementUserService managementUserService;

  @Override
  public void create(SignupDataDto signupDataDto) throws EmailNotificationException, DataNotFoundException, AlreadyExistException, ValidationException, EncryptedException {
    if (signupDataDto.isValid()) {
      if (managementUserService.isUnique(signupDataDto.getEmail())) {
        User user = new User();
        Equalizer.equalize(signupDataDto, user);
        Sender sender = emailNotifierService.getSender();

        sendEmailToUser(user);
        sendEmailToAdmin(sender, user);
        userDao.persist(user);

      } else {
        throw new AlreadyExistException();
      }
    } else {
      throw new ValidationException();
    }
  }

  @Override
  public void create(OtusInitializationConfigDto initializationConfigDto) throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException {
    if (initializationConfigDto.isValid()) {
      if (managementUserService.isUnique(initializationConfigDto.getEmailSender().getEmail())) {
        User user = SystemConfigBuilder.buildInitialUser(initializationConfigDto);
        emailNotifierService.sendSystemInstallationEmail(initializationConfigDto);
        userDao.persist(user);

      } else {
        throw new AlreadyExistException();
      }
    } else {
      throw new ValidationException();
    }
  }

  private void sendEmailToUser(User user) {
    GenericCommunicationData genericCommunicationData = CommunicationDataBuilder.newUserGreeting(user);

    try {
      CommunicationGatewayService emailSender = new CommunicationGatewayService();
      emailSender.sendMail(genericCommunicationData.toJson());
//      log("success");

    } catch (ReadRequestException | MalformedURLException ex) {
      ex.printStackTrace();
    }
  }

  private void sendEmailToAdmin(Sender sender, User userToRegister) throws EmailNotificationException {
    User systemAdministrator = userDao.findAdmin();
    GenericCommunicationData notificationData = CommunicationDataBuilder.newUserNotification(systemAdministrator, userToRegister);

    try {
      CommunicationGatewayService emailSender = new CommunicationGatewayService();
      emailSender.sendMail(notificationData.toJson());
//      log("success");

    } catch (ReadRequestException | MalformedURLException ex) {
      ex.printStackTrace();
    }
  }
}
