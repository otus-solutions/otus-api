package br.org.otus.communication;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;
import br.org.otus.response.info.Validation;
import br.org.otus.user.management.ManagementUserService;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.nimbusds.jwt.SignedJWT;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MessageCommunicationFacade {
  private Participant participant;

  private FieldCenter fieldCenter;

  private User user;

  @Inject
  private IssueMessageDTO issueMessageDTO;

  @Inject
  private ParticipantService participantService;

  @Inject
  private FieldCenterService fieldCenterService;

  @Inject
  private MessageDTO messageDTO;

  @Inject
  private ManagementUserService managementUserService;

  public Object createIssue(String token, String issueJson) {
    try {
      IssueMessageDTO issueMessage = issueMessageDTO.deserialize(issueJson);

      List<String> result = findByEmail(getExtractToEmail(token));

      issueMessage.setSender(result.get(0));
      issueMessage.setGroup(result.get(1));

      GatewayResponse gatewayResponse = new CommunicationGatewayService().createIssue(issueMessageDTO.serialize(issueMessage));

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (ParseException | DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getIssue(String token) {
    try {
      participant = participantService.getByEmail(getExtractToEmail(token));
      //todo: or user
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getIssuesBySender(String.valueOf(participant.getId()));

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (ParseException | DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object createMessage(String token, String id, String messageJson) {
    try {
      MessageDTO message = messageDTO.deserialize(messageJson);

      List<String> result = findByEmail(getExtractToEmail(token));

      message.setSender(result.get(0));
      GatewayResponse gatewayResponse = new CommunicationGatewayService().createMessage(id, messageDTO.serialize(message));

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (ParseException | DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
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

  public Object getMessageByIssueId(String issueId) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getMessageByIssueId(issueId);

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getMessageByIssueIdLimit(String issueId, String skip, String limit) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getMessageByIdLimit(issueId, skip, limit);

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getSenderById(String id)  {
    try {
      return findById(id);
    } catch (DataNotFoundException | JsonSyntaxException e) {
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

  private Object findById(String id) throws DataNotFoundException {
    ObjectId objectId = new ObjectId(id);
    try {
      return participantService.getId(objectId);
    } catch (DataNotFoundException e) {
      return managementUserService.getById(objectId);
    }
  }

  //todo: refactor
  private List findByEmail(String email) throws DataNotFoundException {
    List<String> array = new ArrayList<>();

    try {
      participant = participantService.getByEmail(email);
      fieldCenter = fieldCenterService.fetchByAcronym(participant.getFieldCenter().getAcronym());
      array.add(String.valueOf(participant.getId()));
      array.add(String.valueOf(fieldCenter.getId()));
      return array;
    } catch (DataNotFoundException e) {
      user = managementUserService.fetchByEmail(email);

      array.add(String.valueOf(user.get_id()));
      if (user.getFieldCenter() != null) {
        fieldCenter = fieldCenterService.fetchByAcronym(user.getFieldCenter().getAcronym());
        array.add(String.valueOf(fieldCenter.getId()));
      } else {
        array.add(null);
      }

      return array;
    }
  }

  private String getExtractToEmail (String token)  throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    String mode = signedJWT.getJWTClaimsSet().getClaim("mode").toString(); //user ou participant
    String email = signedJWT.getJWTClaimsSet().getClaim("iss").toString();

    return email;
  }
}
