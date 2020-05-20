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
  @Path("/issue/create")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createIssue(String messageJson) {
    //TODO create DTO
    return new Response().buildSuccess(communicationMessageFacade.createIssue(messageJson)).toJson();
  }

  @POST
  @Path("/issue/message/")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createMessage(String messageJson) {
    return new Response().buildSuccess(communicationMessageFacade.createMessage(messageJson)).toJson();
  }

  @PUT
  @Path("/issue/{id}/reopen/")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateReopen(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.updateReopen(id)).toJson();
  }

  @PUT
  @Path("/issue/{id}/close")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateClose(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.updateClose(id)).toJson();
  }

  @GET
  @Path("/issue/{id}/messages")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageById(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.getMessageById(id)).toJson();
  }

  @GET
  @Path("/issue/{id}/messages/{limit}")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageByIdLimit(@PathParam("id") String id, @PathParam("limit") String limit) {
    return new Response().buildSuccess(communicationMessageFacade.getMessageByIdLimit(id, limit)).toJson();
  }

  @GET
  @Path("/issue/list")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String listIssue() {
    return new Response().buildSuccess(communicationMessageFacade.listIssue()).toJson();
  }

}