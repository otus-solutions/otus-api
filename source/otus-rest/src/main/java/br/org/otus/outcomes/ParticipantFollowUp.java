package br.org.otus.outcomes;

import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;
import br.org.otus.staticVariable.StaticVariableFacade;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("participant-followUp")
public class ParticipantFollowUp {

  @Inject
  private StaticVariableFacade staticVariableFacade;

  @POST
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String listEvent(String variablesJson) {
    return new Response().buildSuccess().toJson();
  }

}
