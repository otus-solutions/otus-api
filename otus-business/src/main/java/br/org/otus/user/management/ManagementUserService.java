package br.org.otus.user.management;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.model.User;
import br.org.otus.user.dto.ManagementUserDto;

public interface ManagementUserService {
    List<ManagementUserDto> list();

    User fetchByEmail(String email);

    void enable(ManagementUserDto managementUserDto) throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException;

    void disable(ManagementUserDto managementUserDto) throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException;

    Boolean isUnique(String emailToVerify);
}
