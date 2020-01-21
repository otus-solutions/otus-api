package br.org.otus.outcomes;

import br.org.otus.gateway.gates.OutcomeGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;
import br.org.otus.response.info.Validation;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.bson.Document;
import org.ccem.otus.model.survey.activity.variables.StaticVariableRequestDTO;

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class FollowUpFacade {

  public Object createFollowUp(String FollowUpJson) {
    try {
      GatewayResponse followUpId = new OutcomeGatewayService().createFollowUp(FollowUpJson);
      return new GsonBuilder().create().fromJson((String) followUpId.getData(), Document.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object updateFollowUp(String FollowUpJson) {
    try {
      return new OutcomeGatewayService().updateFollowUp(FollowUpJson);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object deactivateFollowUp(String FollowUpId) {
    try {
      return new OutcomeGatewayService().deactivateFollowUp(FollowUpId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object listFollowUps() {
    try {
      GatewayResponse gatewayResponse = new OutcomeGatewayService().listFollowUps();
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }
}
