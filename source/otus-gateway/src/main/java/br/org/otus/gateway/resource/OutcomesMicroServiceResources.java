package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class OutcomesMicroServiceResources extends MicroservicesResources {
  private static final String CREATE_FOLLOWUP_RESOURCE = "/followUp/add";
  private static final String UPDATE_FOLLOWUP_RESOURCE = "/followUp/update";
  private static final String DEACTIVATED_FOLLOWUP_RESOURCE = "/followUp/deactivate";
  private static final String LIST_FOLLOWUPS_RESOURCE = "/followUp/list";
  private static final String CREATE_FOLLOWUP_EVENT_RESOURCE = "/event/create";
  private static final String REMOVE_FOLLOWUP_EVENT_RESOURCE = "/event/remove";
  private static final String START_PARTICIPANT_EVENT_RESOURCE = "/participantEvent/start";
  private static final String LIST_PARTICIPANT_EVENT_RESOURCE = "/participantEvent/listAll";
  private static final String CANCEL_PARTICIPANT_EVENT_RESOURCE = "/participantEvent/cancel";
  private static final String ACCOMPLISHED_PARTICIPANT_EVENT_RESOURCE = "/participantEvent/accomplished";
  private static final String NOTIFICATION_DATA_EVENT_RESOURCE = "/event/notification-data";

  public OutcomesMicroServiceResources() {
    super(MicroservicesEnvironments.OUTCOMES);
  }

  public URL getCreateFollowUpAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + CREATE_FOLLOWUP_RESOURCE);
  }

  public URL getUpdateFollowUpAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_FOLLOWUP_RESOURCE);
  }

  public URL getDeactivateFollowUpsAddress(String followUpId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + DEACTIVATED_FOLLOWUP_RESOURCE + "/" + followUpId);
  }

  public URL getListFollowUpsAddress(String participantId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + LIST_FOLLOWUPS_RESOURCE + "/" + participantId);
  }

  public URL getListFollowUpsAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + LIST_FOLLOWUPS_RESOURCE);
  }

  public URL getCreateFollowUpEventAddress(String followUpId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + CREATE_FOLLOWUP_EVENT_RESOURCE + "/" + followUpId);
  }

  public URL getRemoveFollowUpEventAddress(String eventId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + REMOVE_FOLLOWUP_EVENT_RESOURCE + "/" + eventId);
  }

  public URL getStartParticipantEventAddress(String participantId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + START_PARTICIPANT_EVENT_RESOURCE + "/" + participantId);
  }

  public URL getCancelParticipantEventAddress(String eventId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + CANCEL_PARTICIPANT_EVENT_RESOURCE + "/" + eventId);
  }

  public URL getSearchParticipantEventAddress(String participantId, String eventId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + "/participantEvent/" + participantId + "/search/" + eventId);
  }

  public URL getAccomplishedParticipantEventAddress(String eventId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + ACCOMPLISHED_PARTICIPANT_EVENT_RESOURCE + "/" + eventId);
  }

  public URL listAllParticipantEvents(String rn) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + LIST_PARTICIPANT_EVENT_RESOURCE + "/" + rn);
  }

  public URL getNotificationDataEventAddress(String id) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + NOTIFICATION_DATA_EVENT_RESOURCE + "/" + id);
  }
}
