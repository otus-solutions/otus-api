package br.org.otus.user.management;

import br.org.otus.communication.CommunicationDataBuilder;
import br.org.otus.communication.GenericCommunicationData;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.email.user.management.DisableUserNotificationEmail;
import br.org.otus.email.user.management.EnableUserNotificationEmail;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.PasswordResetDto;
import br.org.otus.user.signup.SignupService;
import br.org.tutty.Equalizer;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ManagementUserServiceBean implements ManagementUserService {
  @Inject
  private UserDao userDao;

  @Inject
  private FieldCenterDao fieldCenterDao;

  @Inject
  private EmailNotifierService emailNotifierService;

  @Inject
  private SecurityFacade securityFacade;

  private final static Logger LOGGER = Logger.getLogger(SignupService.class.getName());


  @Override
  public User fetchByEmail(String email) throws DataNotFoundException {
    return userDao.fetchByEmail(email);
  }

  @Override
  public void enable(ManagementUserDto managementUserDto)
    throws ValidationException, DataNotFoundException {
    if (managementUserDto.isValid()) {
      User user = fetchByEmail(managementUserDto.getEmail());
      user.enable();

      userDao.update(user);

      GenericCommunicationData genericCommunicationData = CommunicationDataBuilder.enableUser(user.getEmail());

      try {
        CommunicationGatewayService emailSender = new CommunicationGatewayService();
        emailSender.sendMail(genericCommunicationData.toJson());

      } catch (ReadRequestException | MalformedURLException ex) {
        LOGGER.severe("enable: " + user.getEmail());
      }
    } else {
      throw new ValidationException();
    }
  }

  @Override
  public void disable(ManagementUserDto managementUserDto)
    throws ValidationException, DataNotFoundException {
    if (managementUserDto.isValid()) {
      User user = fetchByEmail(managementUserDto.getEmail());

      if (!user.isAdmin()) {
        user.disable();

        userDao.update(user);

        GenericCommunicationData genericCommunicationData = CommunicationDataBuilder.disableUser(user.getEmail());

        try {
          CommunicationGatewayService emailSender = new CommunicationGatewayService();
          emailSender.sendMail(genericCommunicationData.toJson());

        } catch (ReadRequestException | MalformedURLException ex) {
          LOGGER.severe("disable: " + user.getEmail());
        }
      }
    } else {
      throw new ValidationException();
    }
  }

  @Override
  public void enableExtraction(ManagementUserDto managementUserDto) throws ValidationException, DataNotFoundException {
    if (managementUserDto.isValid()) {
      User user = fetchByEmail(managementUserDto.getEmail());
      user.enableExtraction();
      userDao.update(user);
    } else {
      throw new ValidationException();
    }
  }

  @Override
  public void disableExtraction(ManagementUserDto managementUserDto) throws ValidationException, DataNotFoundException {
    if (managementUserDto.isValid()) {
      User user = fetchByEmail(managementUserDto.getEmail());
      user.disableExtraction();
      userDao.update(user);
    } else {
      throw new ValidationException();
    }
  }

  @Override
  public void updateExtractionIps(ManagementUserDto managementUserDto)
    throws ValidationException, DataNotFoundException {
    if (managementUserDto.isValid()) {
      User user = fetchByEmail(managementUserDto.getEmail());
      user.setExtractionIps(managementUserDto.extractionIps);
      userDao.update(user);
    } else {
      throw new ValidationException();
    }
  }

  @Override
  public Boolean isUnique(String emailToVerify) {
    if (emailToVerify != null && userDao.emailExists(emailToVerify)) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public List<ManagementUserDto> list() {
    List<ManagementUserDto> administrationUsersDtos = new ArrayList<>();
    List<User> users = userDao.fetchAll();

    users.stream().forEach(user -> {
      ManagementUserDto managementUserDto = new ManagementUserDto();

      Equalizer.equalize(user, managementUserDto);
      if (user.getFieldCenter() != null) {
        managementUserDto.fieldCenter.acronym = user.getFieldCenter().getAcronym();
      }
      administrationUsersDtos.add(managementUserDto);
    });

    return administrationUsersDtos;
  }

  @Override
  public void updateFieldCenter(ManagementUserDto managementUserDto) throws DataNotFoundException {
    User user = fetchByEmail(managementUserDto.getEmail());
    if (!managementUserDto.fieldCenter.acronym.isEmpty()) {
      FieldCenter fieldCenter = fieldCenterDao.fetchByAcronym(managementUserDto.fieldCenter.acronym);
      user.setFieldCenter(fieldCenter);
    } else {
      user.setFieldCenter(null);
    }
    userDao.update(user);
  }

  @Override
  public void requestPasswordReset(PasswordResetRequestDto requestData) {

    GenericCommunicationData genericCommunicationData = CommunicationDataBuilder.userPasswordReset(requestData.getEmail(), requestData.getToken(),
      requestData.getRedirectUrl());

    try {
      CommunicationGatewayService emailSender = new CommunicationGatewayService();
      emailSender.sendMail(genericCommunicationData.toJson());

    } catch (ReadRequestException | MalformedURLException ex) {
      LOGGER.severe("requestPasswordReset: " + requestData.getEmail());
    }
  }

  public void updateUserPassword(PasswordResetDto passwordResetDto) throws EncryptedException {
    passwordResetDto.encrypt();
    userDao.updatePassword(passwordResetDto.getEmail(), passwordResetDto.getPassword());
    securityFacade.removePasswordResetRequests(passwordResetDto.getEmail());
  }

  @Override
  public User getById(ObjectId userId) throws DataNotFoundException {
    return  userDao.getById(userId);
  }

}
