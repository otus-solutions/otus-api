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

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

public class CommunicationMessageFacade {

  public Object createMessage(String MessageJson) {
    try {
      GatewayResponse gatewayResponse = new CommunicationGatewayService().createMessage(MessageJson);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Document.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object updateMessage(String messageId, String message) {
    try {
      return new CommunicationGatewayService().updateMessage(messageId,message);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getMessageById(String messageId) {
    try {
      return new CommunicationGatewayService().getMessageById(messageId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object getAllMessage() {
    try {
      return new CommunicationGatewayService().getAllMessage();
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

}
