package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonGETUtility;
import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.request.JsonPUTRequestUtility;
import br.org.otus.gateway.resource.OutcomesMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OutcomeGatewayService {
  public GatewayResponse createFollowUp(String followUpJson) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getCreateFollowUpAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, followUpJson);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }

  }

  public GatewayResponse updateFollowUp(String followUpJson) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getUpdateFollowUpAddress();
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.writeBody(followUpJson);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse deactivateFollowUp(String followUpId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getDeactivateFollowUpsAddress(followUpId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse listFollowUps() throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getListFollowUpsAddress();
    try {
      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse listFollowUpsByParticipant(String participantId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getListFollowUpsAddress(participantId);
    try {
      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse createFollowUpEvent(String followUpId, String eventJson) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getCreateFollowUpEventAddress(followUpId);
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, eventJson);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse startParticipantEvent(String participantId, String eventJson) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getStartParticipantEventAddress(participantId);
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, eventJson);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse cancelParticipantEvent(String eventId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getCancelParticipantEventAddress(eventId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse searchParticipantEvent(String participantId, String eventId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getSearchParticipantEventAddress(participantId, eventId);
    try {
      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse accomplishedParticipantEvent(String eventId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getAccomplishedParticipantEventAddress(eventId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      String response = jsonPUT.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse accomplishedParticipantEventByActivity(String activityId) throws IOException {
    URL requestURL = new OutcomesMicroServiceResources().getAccomplishedParticipantEventAddressByActivity(activityId);
    JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
    String response = jsonPUT.finish();
    return new GatewayResponse().buildSuccess(response);
  }

  public GatewayResponse reopenedParticipantEventByActivity(String activityId) throws IOException {
    URL requestURL = new OutcomesMicroServiceResources().getReopenedParticipantEventAddressByActivity(activityId);
    JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
    String response = jsonPUT.finish();
    return new GatewayResponse().buildSuccess(response);
  }

  public GatewayResponse removeFollowUpEvent(String eventId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getRemoveFollowUpEventAddress(eventId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse listAllParticipantEvents(String rn) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().listAllParticipantEvents(rn);
    try {
      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse getNotificationDataEvent(String eventId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getNotificationDataEventAddress(eventId);
    try {
      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse cancelParticipantEventByActivityId(String activityID) throws MalformedURLException, ReadRequestException, RequestException {
    URL requestURL = new OutcomesMicroServiceResources().cancelParticipantEventByActivityId(activityID);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      String response = jsonPUT.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException(ex.getMessage(), ex.getCause());
    }
  }
}