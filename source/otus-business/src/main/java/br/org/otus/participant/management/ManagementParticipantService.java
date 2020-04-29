package br.org.otus.participant.management;

import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import java.net.MalformedURLException;

public interface ManagementParticipantService {

  void requestPasswordReset(PasswordResetRequestDto requestData) throws EncryptedException, DataNotFoundException, EmailNotificationException, MalformedURLException;
}
