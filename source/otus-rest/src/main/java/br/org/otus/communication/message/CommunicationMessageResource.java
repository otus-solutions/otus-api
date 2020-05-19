package br.org.otus.communication.message;

import br.org.otus.communication.CommunicationMessageFacade;
import br.org.otus.security.user.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;

@Path("/communicationMessage")
public class CommunicationMessageResource {

  @Inject
  private CommunicationMessageFacade communicationMessageFacade;

  @POST
  @Path("/create-message")
//  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateFollowUp(String messageJson) {
    return new Response().buildSuccess(communicationMessageFacade.createMessage(messageJson)).toJson();
  }
}
