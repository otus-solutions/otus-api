package br.org.otus.user.management;

import br.org.otus.model.User;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.PasswordResetDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface ManagementUserService {
  List<ManagementUserDto> list();

  User fetchByEmail(String email) throws DataNotFoundException;

  void enable(ManagementUserDto managementUserDto)
    throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException;

  void disable(ManagementUserDto managementUserDto)
    throws ValidationException, DataNotFoundException;

  void enableExtraction(ManagementUserDto managementUserDto) throws ValidationException, DataNotFoundException;

  void disableExtraction(ManagementUserDto managementUserDto) throws ValidationException, DataNotFoundException;

  void updateFieldCenter(ManagementUserDto managementUserDto) throws DataNotFoundException;

  void updateExtractionIps(ManagementUserDto managementUserDto) throws ValidationException, DataNotFoundException;

  Boolean isUnique(String emailToVerify);

  void requestPasswordReset(PasswordResetRequestDto requestData) throws EncryptedException, DataNotFoundException, EmailNotificationException;

  void updateUserPassword(PasswordResetDto passwordResetDto) throws EncryptedException;

  User getById(ObjectId userId) throws DataNotFoundException;
}
