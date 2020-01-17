package br.org.otus.outcomes;

import br.org.otus.gateway.gates.DBDistributionGatewayService;
import br.org.otus.gateway.gates.OutcomeGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import com.google.gson.JsonSyntaxException;
import org.ccem.otus.model.survey.activity.variables.StaticVariableRequestDTO;

import java.net.MalformedURLException;

public class FollowUpFacade {

  public Object createFollowUp(String FollowUpJson) {
    try {
      GatewayResponse followUpId = new OutcomeGatewayService().createFollowUp(FollowUpJson);
      return StaticVariableRequestDTO.deserialize((String) followUpId.getData());
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public Object updateFollowUp(String FollowUpJson) {
    try {
      GatewayResponse followUpId = new OutcomeGatewayService().updateFollowUp(FollowUpJson);
      return StaticVariableRequestDTO.deserialize((String) followUpId.getData());
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public Object deactivateFollowUp(String FollowUpId) {
    try {
      GatewayResponse followUpId = new OutcomeGatewayService().deactivateFollowUp(FollowUpId);
      return StaticVariableRequestDTO.deserialize((String) followUpId.getData());
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public Object listFollowUps() {
    try {
      GatewayResponse followUpId = new OutcomeGatewayService().listFollowUps();
      return StaticVariableRequestDTO.deserialize((String) followUpId.getData());
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }
}
