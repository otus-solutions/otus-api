package br.org.otus.staticVariable;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("static-variable")
public class StaticVariableResource {

  @Inject
  private StaticVariableFacade staticVariableFacade;

  @POST
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String listVariables(String variablesJson) {

    return new Response().buildSuccess(staticVariableFacade.listVariables(variablesJson)).toJson();
  }
}
