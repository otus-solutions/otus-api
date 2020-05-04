package br.org.otus.participant.management;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.participant.enums.ParticipantDefinitions;
import br.org.otus.security.dtos.ParticipantCommunicationDataDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;

import java.net.MalformedURLException;
import java.net.URL;

public class ManagementParticipantServiceBean implements ManagementParticipantService {

  @Override
  public void requestPasswordReset(PasswordResetRequestDto requestData)
    throws EmailNotificationException {
    try {
      ParticipantCommunicationDataDto participantCommunicationDataDto = new ParticipantCommunicationDataDto();
      participantCommunicationDataDto.setEmail(requestData.getEmail());
      participantCommunicationDataDto.setId(ParticipantDefinitions.TEMPLATE_RESET_PASSWD_PARTICIPANT_ID.getValue());
      participantCommunicationDataDto.pushVariable("token", requestData.getToken());
      participantCommunicationDataDto.pushVariable("host", String.valueOf(isValidURL(requestData.getRedirectUrl())));
      new CommunicationGatewayService().sendMail(ParticipantCommunicationDataDto.serialize(participantCommunicationDataDto));

    }catch(MalformedURLException | RequestException e){
      throw new EmailNotificationException(e);
    }
  }

  private URL isValidURL(String redirectUrl) throws MalformedURLException {
    return new URL(redirectUrl);
  }
}
