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
  private static final String FIND_MESSAGE_COMMUNICATION_RESOURCE = "/api/find-message-communication";
  private static final String GET_ALL_MESSAGE_COMMUNICATION_RESOURCE = "/api/get-all-message-communication";
  private static final String UPDATE_MESSAGE_COMMUNICATION_RESOURCE = "/api/update-message-communication";
  private static final String DELETE_MESSAGE_COMMUNICATION_RESOURCE = "/api/delete-message-communication";

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

  public URL getMessageCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + MESSAGE_COMMUNICATION_RESOURCE);
  }

  public URL getMessageByIdCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + FIND_MESSAGE_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getAllMessageCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + GET_ALL_MESSAGE_COMMUNICATION_RESOURCE);
  }

  public URL getUpdateMessageCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_MESSAGE_COMMUNICATION_RESOURCE);
  }

  public URL getDeleteMessageCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + DELETE_MESSAGE_COMMUNICATION_RESOURCE + "/" + ID);
  }

}
