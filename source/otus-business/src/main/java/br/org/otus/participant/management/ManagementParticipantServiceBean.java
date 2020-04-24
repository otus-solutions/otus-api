package br.org.otus.participant.management;

import br.org.otus.email.user.management.PasswordResetEmail;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class ManagementParticipantServiceBean implements ManagementParticipantService{

  @Override
  public void requestPasswordReset(PasswordResetRequestDto requestData)
    throws EncryptedException, DataNotFoundException, EmailNotificationException {

    PasswordResetEmail passwordResetEmail = new PasswordResetEmail(requestData.getToken(),
      requestData.getRedirectUrl());
    passwordResetEmail.defineRecipient(requestData.getEmail());
//    passwordResetEmail.setFrom(emailNotifierService.getSender());

  }
}
