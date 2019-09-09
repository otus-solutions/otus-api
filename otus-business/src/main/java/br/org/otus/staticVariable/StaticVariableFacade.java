package br.org.otus.staticVariable;

import java.net.MalformedURLException;

import org.ccem.otus.model.survey.activity.variables.StaticVariableRequestDTO;

import com.google.gson.JsonSyntaxException;

import br.org.otus.gateway.gates.DBDistributionGateway;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;

public class StaticVariableFacade {

  public StaticVariableRequestDTO listVariables(String listVariables) {
    try {
      GatewayResponse variables = new DBDistributionGateway().findVariables(listVariables);
      return StaticVariableRequestDTO.deserialize((String) variables.getData());
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

}
