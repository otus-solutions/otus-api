package br.org.otus.participant.management;

import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;

import java.net.MalformedURLException;

public interface ManagementParticipantService {

  void requestPasswordReset(PasswordResetRequestDto requestData) throws EmailNotificationException;
}
