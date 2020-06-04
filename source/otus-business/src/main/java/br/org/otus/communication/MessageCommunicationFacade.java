package br.org.otus.communication;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;
import br.org.otus.response.info.Validation;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.FieldCenterService;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MessageCommunicationFacade {
  private Participant participant;

  private FieldCenter fieldCenter;

  @Inject
  private IssueMessageDTO issueMessageDTO;

  @Inject
  private ParticipantService participantService;

  @Inject
  private FieldCenterService fieldCenterService;

  @Inject
  private ParticipantFilterDTO participantFilterDTO;

  @Inject
  private MessageDTO messageDTO;

  public Object createIssue(String userEmail, String issueJson) {
    try {
      IssueMessageDTO issueMessage = issueMessageDTO.deserialize(issueJson);
      participant = participantService.getByEmail(userEmail);
      fieldCenter = fieldCenterService.fetchByAcronym(participant.getFieldCenter().getAcronym());
      issueMessage.setSender(String.valueOf(participant.getId()));
      issueMessage.setGroup(String.valueOf(fieldCenter.getId()));

      GatewayResponse gatewayResponse = new CommunicationGatewayService().createIssue(issueMessageDTO.serialize(issueMessage));
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object createMessage(String userEmail, String id, String messageJson) {
    try {
      MessageDTO message = messageDTO.deserialize(messageJson);
      participant = participantService.getByEmail(userEmail);

      message.setSender(String.valueOf(participant.getId()));
      GatewayResponse gatewayResponse = new CommunicationGatewayService().createMessage(id, messageDTO.serialize(message));
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object filter(String filterJson) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().filter(filterJson);

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object updateReopen(String issueId) {
    try {
      return new CommunicationGatewayService().updateReopen(issueId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object updateClose(String issueId) {
    try {
      return new CommunicationGatewayService().updateClose(issueId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object updateFinalize(String issueId) {
    try {
      return new CommunicationGatewayService().updateFinalize(issueId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getMessageById(String issueId) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getMessageById(issueId);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getMessageByIdLimit(String issueId, String limit) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getMessageByIdLimit(issueId, limit);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object listIssue(String userEmail) {
    try {
      participant = participantService.getByEmail(userEmail);
      GatewayResponse gatewayResponse = new CommunicationGatewayService().listIssue(String.valueOf(participant.getId()));
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getSenderById(String id) {
    try {
      ObjectId objectId = new ObjectId(id);
      return participantService.getId(objectId);
    } catch (JsonSyntaxException | DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public Object getIssueById(String id) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getIssueById(id);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getIssuesByRn(String rn) {
    try {
      participant = participantService.getByRecruitmentNumber(Long.valueOf(rn));
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getIssueByRn(String.valueOf(participant.getId()));

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }
}
