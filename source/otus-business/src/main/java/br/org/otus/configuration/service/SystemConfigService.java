package br.org.otus.configuration.service;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public interface SystemConfigService {

  void initConfiguration(OtusInitializationConfigDto initializationConfigDto, String projectToken) throws EncryptedException, EmailNotificationException, AlreadyExistException;

  void verifyEmailService(OtusInitializationConfigDto initializationData) throws EmailNotificationException, EncryptedException;

  String buildToken();

  Boolean isReady();

}
