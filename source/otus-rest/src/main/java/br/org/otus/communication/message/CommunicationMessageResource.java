package br.org.otus.communication.message;

import br.org.otus.communication.CommunicationMessageFacade;
import br.org.otus.security.user.Secured;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;

@Path("/communication-message")
public class CommunicationMessageResource {

  @Inject
  private CommunicationMessageFacade communicationMessageFacade;

  @POST
  @Path("/create-message")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createMessage(String messageJson) {
    return new Response().buildSuccess(communicationMessageFacade.createMessage(messageJson)).toJson();
  }

  @PUT
  @Path("/update-message/{id}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateMessage(@PathParam("id") String id, String messageJson) {
    return new Response().buildSuccess(communicationMessageFacade.updateMessage(id, messageJson)).toJson();
  }

  @GET
  @Path("/find-message/{id}")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageById(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.getMessageById(id)).toJson();
  }

  @GET
  @Path("/get-all-message")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String getAllMessage() {
    return new Response().buildSuccess(communicationMessageFacade.getAllMessage()).toJson();
  }

  @DELETE
  @Path("/delete-message/{id}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String deleteMessage(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.deleteMessage(id)).toJson();
  }
}