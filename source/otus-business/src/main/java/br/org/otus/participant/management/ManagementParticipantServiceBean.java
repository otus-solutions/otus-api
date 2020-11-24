package br.org.otus.participant.management;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.security.dtos.ParticipantCommunicationDataDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.communication.TemplateEmailKeys;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class ManagementParticipantServiceBean implements ManagementParticipantService {
  private static Logger LOGGER = Logger.getLogger("br.org.otus.participant.management.ManagementParticipantServiceBean");

  @Override
  public void requestPasswordReset(PasswordResetRequestDto requestData) throws EmailNotificationException {
    try {
      ParticipantCommunicationDataDto participantCommunicationDataDto = new ParticipantCommunicationDataDto();
      participantCommunicationDataDto.setEmail(requestData.getEmail());
      participantCommunicationDataDto.setId(TemplateEmailKeys.RESET_PASSWD_PARTICIPANT.getValue());
      participantCommunicationDataDto.pushVariable("token", requestData.getToken());
      participantCommunicationDataDto.pushVariable("host", String.valueOf(isValidURL(requestData.getRedirectUrl())));
      GatewayResponse notification = new CommunicationGatewayService().sendMail(ParticipantCommunicationDataDto.serialize(participantCommunicationDataDto));
      logNotification("requestPasswordReset", notification.getData(), true, null);
    } catch (MalformedURLException | RequestException | ReadRequestException ex) {
      if (ex instanceof ReadRequestException)
        logNotification("requestPasswordReset", ex, false, requestData.getEmail());
      throw new EmailNotificationException(ex);
    }
  }

  private void logNotification(String action, Object notification, Boolean success, String email) {
    if (success) {
      LOGGER.info("action: " + action + ", status: success, info: " + notification);
      return;
    }
    LOGGER.severe("action: " + action + ", status: fail, email: " + email + ", info: " + notification);
  }

  @Override
  public String requestPasswordResetLink(PasswordResetRequestDto requestData) throws MalformedURLException {
    String resource = "/#/register-password/";
    String url = String.valueOf(isValidURL(requestData.getRedirectUrl() + resource));
    String token = requestData.getToken();

    return url + token;
  }


  private URL isValidURL(String redirectUrl) throws MalformedURLException {
    return new URL(redirectUrl);
  }
}
