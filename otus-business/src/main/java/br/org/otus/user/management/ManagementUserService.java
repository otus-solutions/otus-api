package br.org.otus.user.management;

import br.org.otus.model.User;
import br.org.otus.user.dto.ManagementUserDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface ManagementUserService {
    List<ManagementUserDto> list();

    User fetchByEmail(String email);

    void enable(ManagementUserDto managementUserDto) throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException;

    void disable(ManagementUserDto managementUserDto) throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException;

    Boolean isUnique(String emailToVerify);
}
