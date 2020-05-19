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

  public Object createMessage(String MessageJson) {//TODO not return ID Message add void
    try {
      GatewayResponse messageId = new CommunicationGatewayService().createMessage(MessageJson);
      return new GsonBuilder().create().fromJson((String) messageId.getData(), Document.class);//TODO return ID Message
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

}
