package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationMicroServiceResources extends MicroservicesResources {
  private static final String COMMUNICATION_RESOURCE = "/api/communication";
  private static final String CREATE_COMMUNICATION_RESOURCE = "/api/create-communication";
  private static final String FIND_COMMUNICATION_RESOURCE = "/api/find-communication";
  private static final String GET_ALL_COMMUNICATION_RESOURCE = "/api/get-all-communication";
  private static final String UPDATE_COMMUNICATION_RESOURCE = "/api/update-communication";
  private static final String DELETE_COMMUNICATION_RESOURCE = "/api/delete-communication";
  private static final String MESSAGE_COMMUNICATION_RESOURCE = "/api/message-communication";
  private static final String FIND_LIST_MESSAGE_COMMUNICATION_RESOURCE = "/api/find-list-message-communication";
  private static final String FIND_LIST_MESSAGE_LIMIT_COMMUNICATION_RESOURCE = "/api/find-list-message-limit-communication";
  private static final String ISSUE_COMMUNICATION_RESOURCE = "/api/issue-create";
  private static final String UPDATE_ISSUE_REOPEN_COMMUNICATION_RESOURCE = "/api/update-issue-reopen-communication";
  private static final String UPDATE_ISSUE_CLOSE_COMMUNICATION_RESOURCE = "/api/update-issue-close-communication";
  private static final String ISSUE_LIST_COMMUNICATION_RESOURCE = "/api/issue-list";

  public CommunicationMicroServiceResources() {
    super(MicroservicesEnvironments.COMMUNICATION_SERVICE);
  }

  public URL getCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + COMMUNICATION_RESOURCE);
  }

  public URL getCreateCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + CREATE_COMMUNICATION_RESOURCE);
  }

  public URL getFindCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + FIND_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getAllCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + GET_ALL_COMMUNICATION_RESOURCE);
  }

  public URL getUpdateCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_COMMUNICATION_RESOURCE);
  }

  public URL getDeleteCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + DELETE_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getMessageCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + MESSAGE_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getMessageByIdCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + FIND_LIST_MESSAGE_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getMessageByIdLimitCommunicationAddress(String ID, String LIMIT) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + FIND_LIST_MESSAGE_LIMIT_COMMUNICATION_RESOURCE + "/" + ID + "/" + LIMIT);
  }

  public URL getIssueCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + ISSUE_COMMUNICATION_RESOURCE);
  }

  public URL getUpdateReopenCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_ISSUE_REOPEN_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getUpdateCloseCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_ISSUE_CLOSE_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getListIssueCommunicationAddress(String USER_EMAIL) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + ISSUE_LIST_COMMUNICATION_RESOURCE+ "/" + USER_EMAIL);
  }
}
