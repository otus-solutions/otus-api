package br.org.otus.user.signup;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.user.dto.SignupDataDto;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

public interface SignupService {

    void create(SignupDataDto signupDataDto) throws AlreadyExistException, EncryptedException, EmailNotificationException, DataNotFoundException, ValidationException;

    void create(OtusInitializationConfigDto initializationConfigDto) throws AlreadyExistException, EmailNotificationException, EncryptedException, ValidationException;
}
