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
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
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

      List<String> result = findByToken(token);

      issueMessage.setSender(result.get(0));
      issueMessage.setGroup(result.get(1));

      GatewayResponse gatewayResponse = new CommunicationGatewayService().createIssue(issueMessageDTO.serialize(issueMessage));

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), String.class);
    } catch (DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    } catch (ParseException | ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public Object getIssue(String token) {
    try {
      List<String> result = findByToken(token);

      final String senderId = result.get(0);
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getIssuesBySender(String.valueOf(senderId));

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (DataNotFoundException | JsonSyntaxException | MalformedURLException | ParseException | ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }


  public Object createMessage(String token, String id, String messageJson) {
    try {
      MessageDTO message = messageDTO.deserialize(messageJson);

      List<String> result = findByToken(token);

      message.setSender(result.get(0));
      GatewayResponse gatewayResponse = new CommunicationGatewayService().createMessage(id, messageDTO.serialize(message));

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), String.class);
    } catch (DataNotFoundException | JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    } catch (ParseException | ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
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

  public Object getMessageByIssueIdLimit(String issueId, String skip, String limit, String order) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().getMessageByIdLimit(issueId, skip, limit, order);

      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      if(ex.getErrorMessage().equals("Not Found")) return new GsonBuilder().create().fromJson("[]", ArrayList.class);
      else throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getSenderById(String id) {
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
  private List<String> findByToken(String token) throws DataNotFoundException, ValidationException, ParseException {
    List<String> array = new ArrayList<>();
    final String mode = getTokenMode(token);
    final String email = getTokenEmail(token);
    if (mode.equals("user")) {
      user = managementUserService.fetchByEmail(email);

      array.add(String.valueOf(user.get_id()));
      if (user.getFieldCenter() != null) {
        fieldCenter = fieldCenterService.fetchByAcronym(user.getFieldCenter().getAcronym());
        array.add(String.valueOf(fieldCenter.getId()));
      } else {
        array.add(null);
      }

      return array;
    } else if (mode.equals("participant")) {
      participant = participantService.getByEmail(email);
      fieldCenter = fieldCenterService.fetchByAcronym(participant.getFieldCenter().getAcronym());
      array.add(String.valueOf(participant.getId()));
      array.add(String.valueOf(fieldCenter.getId()));
      return array;
    } else {
      throw new ValidationException("Invalid Mode");
    }
  }

  private String getTokenEmail(String token) throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    String email = signedJWT.getJWTClaimsSet().getClaim("iss").toString();
    return email;
  }

  private String getTokenMode(String token) throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    String mode = signedJWT.getJWTClaimsSet().getClaim("mode").toString();
    return mode;
  }
}
