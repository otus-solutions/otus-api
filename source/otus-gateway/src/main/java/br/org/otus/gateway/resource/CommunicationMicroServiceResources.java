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



  private static final String ISSUES_COMMUNICATION_RESOURCE = "/api/project-communication/issues";
  private static final String FILTER_COMMUNICATION_RESOURCE = "/api/project-communication/issues/filter";

  private static final String UPDATE_ISSUES_REOPEN_COMMUNICATION_RESOURCE = "/api/project-communication/issues-reopen";
  private static final String UPDATE_ISSUES_CLOSE_COMMUNICATION_RESOURCE = "/api/project-communication/issues-close";
  private static final String UPDATE_ISSUES_FINALIZE_COMMUNICATION_RESOURCE = "/api/project-communication/issues-finalize";

  private static final String MESSAGES_COMMUNICATION_RESOURCE = "/api/project-communication/messages";
  private static final String FIND_LIMIT_MESSAGES_COMMUNICATION_RESOURCE = "/api/project-communication/messages/limit";

  private static final String ISSUES_SENDER_COMMUNICATION_RESOURCE = "/api/project-communication/issues/sender";

  //============ Issue
  public URL getIssuesCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + ISSUES_COMMUNICATION_RESOURCE);
  }

  public URL getIssueByIdCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + ISSUES_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getIssueBySenderIdCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + ISSUES_SENDER_COMMUNICATION_RESOURCE + "/" + ID);
  }

  //update issue
  public URL getUpdateReopenCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_ISSUES_REOPEN_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getUpdateCloseCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_ISSUES_CLOSE_COMMUNICATION_RESOURCE + "/" + ID);
  }

  public URL getUpdateFinalizeCommunicationAddress(String ID) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_ISSUES_FINALIZE_COMMUNICATION_RESOURCE + "/" + ID);
  }

  //filter issue
  public URL getFilterCommunicationAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + FILTER_COMMUNICATION_RESOURCE);
  }

  //============ Message

  public URL getMessageCommunicationAddress(String issueId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + MESSAGES_COMMUNICATION_RESOURCE + "/" + issueId);
  }

  public URL getMessageByIdCommunicationAddress(String issueId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + MESSAGES_COMMUNICATION_RESOURCE + "/" + issueId);
  }

  public URL getMessageByIdLimitCommunicationAddress(String issueId, String SKIP, String LIMIT) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + FIND_LIMIT_MESSAGES_COMMUNICATION_RESOURCE + "/" + issueId + "/" + SKIP + "/" + LIMIT);
  }
}
