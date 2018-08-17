package br.org.otus.user.management;

import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.email.user.management.DisableUserNotificationEmail;
import br.org.otus.email.user.management.EnableUserNotificationEmail;
import br.org.otus.email.user.management.PasswordResetEmail;
import br.org.otus.model.User;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.user.UserDao;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.PasswordResetDto;
import br.org.tutty.Equalizer;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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


  @Override
  public User fetchByEmail(String email) throws DataNotFoundException {
    return userDao.fetchByEmail(email);
  }

  @Override
  public void enable(ManagementUserDto managementUserDto) throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    if (managementUserDto.isValid()) {
      User user = fetchByEmail(managementUserDto.getEmail());
      user.enable();

      userDao.update(user);

      EnableUserNotificationEmail enableUserNotificationEmail = new EnableUserNotificationEmail();
      enableUserNotificationEmail.defineRecipient(user);
      enableUserNotificationEmail.setFrom(emailNotifierService.getSender());
      emailNotifierService.sendEmail(enableUserNotificationEmail);
    } else {
      throw new ValidationException();
    }
  }

  @Override
  public void disable(ManagementUserDto managementUserDto) throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    if (managementUserDto.isValid()) {
      User user = fetchByEmail(managementUserDto.getEmail());

      if (!user.isAdmin()) {
        user.disable();

        userDao.update(user);

        DisableUserNotificationEmail disableUserNotificationEmail = new DisableUserNotificationEmail();
        disableUserNotificationEmail.defineRecipient(user);
        disableUserNotificationEmail.setFrom(emailNotifierService.getSender());
        emailNotifierService.sendEmail(disableUserNotificationEmail);
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
  public void updateExtractionIps(ManagementUserDto managementUserDto) throws ValidationException, DataNotFoundException {
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
  public void requestPasswordReset(PasswordResetRequestDto requestData) throws EncryptedException, DataNotFoundException, EmailNotificationException {
    PasswordResetEmail passwordResetEmail = new PasswordResetEmail(requestData.getToken(), requestData.getRedirectUrl());
    passwordResetEmail.defineRecipient(requestData.getEmail());
    passwordResetEmail.setFrom(emailNotifierService.getSender());
    emailNotifierService.sendEmail(passwordResetEmail);
  }

  public void updateUserPassword (PasswordResetDto passwordResetDto) {
    //update password

    //then remove all user tokens
    securityFacade.removePasswordResetRequests(passwordResetDto.getEmail());
  }

}
