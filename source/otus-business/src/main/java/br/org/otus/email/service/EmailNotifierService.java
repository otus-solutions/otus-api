package br.org.otus.email.service;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.OtusEmail;
import br.org.owail.sender.email.Sender;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public interface EmailNotifierService {

  void sendSystemInstallationEmail(OtusInitializationConfigDto otusInitializationData) throws EmailNotificationException, EncryptedException;

  Sender getSender() throws EncryptedException, DataNotFoundException;

  void sendEmail(OtusEmail email) throws EmailNotificationException;

  void sendEmailSync(OtusEmail email) throws EmailNotificationException;

}
