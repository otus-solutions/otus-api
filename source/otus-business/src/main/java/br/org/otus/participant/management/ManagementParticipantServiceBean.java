package br.org.otus.participant.management;

import br.org.otus.email.user.management.PasswordResetEmail;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.security.dtos.ParticipantCommunicationDataDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import java.net.MalformedURLException;

public class ManagementParticipantServiceBean implements ManagementParticipantService {

  @Override
  public void requestPasswordReset(PasswordResetRequestDto requestData)
    throws EncryptedException, DataNotFoundException, MalformedURLException {

    ParticipantCommunicationDataDto participantCommunicationDataDto = new ParticipantCommunicationDataDto();
    participantCommunicationDataDto.setEmail(requestData.getEmail());
    participantCommunicationDataDto.setId("5ea88862ae51d800083aeba7");
    participantCommunicationDataDto.pushVariable("token", requestData.getToken());
    participantCommunicationDataDto.pushVariable("host", requestData.getRedirectUrl());

    new CommunicationGatewayService().sendMail(ParticipantCommunicationDataDto.serialize(participantCommunicationDataDto));
  }

}
