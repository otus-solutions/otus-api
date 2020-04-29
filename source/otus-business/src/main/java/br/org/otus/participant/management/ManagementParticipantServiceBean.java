package br.org.otus.participant.management;

import br.org.otus.email.user.management.PasswordResetEmail;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import java.net.MalformedURLException;

public class ManagementParticipantServiceBean implements ManagementParticipantService{

  @Override
  public void requestPasswordReset(PasswordResetRequestDto requestData)
    throws EncryptedException, DataNotFoundException, MalformedURLException {

    PasswordResetEmail passwordResetEmail = new PasswordResetEmail(requestData.getToken(),
      requestData.getRedirectUrl());
    passwordResetEmail.defineRecipient(requestData.getEmail());

    String emailVariables = "{\n" +
      "\t\"email\": \"fdrtec@gmail.com\",\n" +
      "\t\"variables\": {\n" +
      "\t\t\"token\":\"123456-tokenFake\",\n" +
      "\t\t\"host\": \"http://otus-solutions.com.br\"} ,\n" +
      "    \"_id\":\"5ea88862ae51d800083aeba7\"\n" +
      "}";


      new CommunicationGatewayService().sendMail(emailVariables);
  }

}
