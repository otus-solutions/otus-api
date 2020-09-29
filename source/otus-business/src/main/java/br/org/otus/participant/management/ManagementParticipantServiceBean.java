package br.org.otus.participant.management;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.security.dtos.ParticipantCommunicationDataDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.template.enums.TemplateEmailKey;
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
      participantCommunicationDataDto.setId(TemplateEmailKey.TEMPLATE_RESET_PASSWD_PARTICIPANT_ID.getValue());
      participantCommunicationDataDto.pushVariable("token", requestData.getToken());
      participantCommunicationDataDto.pushVariable("host", String.valueOf(isValidURL(requestData.getRedirectUrl())));
      GatewayResponse notification = new CommunicationGatewayService().sendMail(ParticipantCommunicationDataDto.serialize(participantCommunicationDataDto));
      logNotification(notification.getData(), true);
    } catch (MalformedURLException | RequestException e) {
      throw new EmailNotificationException(e);
    } catch(ReadRequestException e){
      logNotification(e, false);
    }
  }


  private void logNotification(Object notification, Boolean success){
    if(success){
      LOGGER.info("action: sendEmail, status: success, info: " +notification);
      return;
    }
    LOGGER.severe("action: sendEmail, status: fail, info: " +notification);
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
