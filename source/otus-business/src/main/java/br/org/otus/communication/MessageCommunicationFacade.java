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
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MessageCommunicationFacade {
  private Participant participant;

  @Inject
  private IssueMessageDTO issueMessageDTO;

  @Inject
  private ParticipantService participantService;

  @Inject
  private ParticipantFilterDTO participantFilterDTO;

  public Object createIssue(String userEmail, String issueJson) {
    try {
      IssueMessageDTO issueMessage = issueMessageDTO.deserialize(issueJson);
      issueMessage.setSender(userEmail);
      GatewayResponse gatewayResponse = new CommunicationGatewayService().createIssue(issueMessageDTO.serialize(issueMessage));
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object createMessage(String id, String messageJson) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().createMessage(id, messageJson);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object filter(String filterJson) {
    try {
//      List<ParticipantFilterDTO> filter = new ArrayList<>(); //TODO review filter
//      List<Object> arrayData = new ArrayList<>();
//      ParticipantFilterDTO participantFilter= new ParticipantFilterDTO();
      GatewayResponse gatewayResponse = new CommunicationGatewayService().filter(filterJson);
//      arrayData = (List<Object>) gatewayResponse.getData();
//
//      arrayData.forEach(issue -> {
//        IssueMessageDTO issueMessage = issueMessageDTO.deserialize((String) issue);
//        String email = issueMessage.getSender();
//        try {
//          participant = participantService.getByEmail(email);
//        } catch (DataNotFoundException e) {
//          new HttpResponseException(Validation.build(e.getCause().getMessage()));
//        }
//
//        participantFilter.setIssueMessageDTO(issueMessage);
//        participantFilter.setCenter(participant.getFieldCenter().getAcronym());
//        participantFilter.setName(participant.getName());
//        participantFilter.setRn(participant.getRecruitmentNumber());
//
//        filter.add(participantFilter);
//      });

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
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
      GatewayResponse gatewayResponse = new CommunicationGatewayService().listIssue(userEmail);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }
}
