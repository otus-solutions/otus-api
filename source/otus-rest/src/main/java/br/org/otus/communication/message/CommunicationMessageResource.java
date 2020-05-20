package br.org.otus.communication.message;

import br.org.otus.communication.CommunicationMessageFacade;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.user.Secured;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;

@Path("/communication-message")
public class CommunicationMessageResource {

  @Inject
  private CommunicationMessageFacade communicationMessageFacade;

  @Inject
  private SecurityContext securityContext;

  @POST
  @Secured
  @Path("/issue/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createIssue(@Context HttpServletRequest request, String messageJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

    //TODO create DTO
    return new Response().buildSuccess(communicationMessageFacade.createIssue(messageJson)).toJson();
  }

  @POST
  @Secured
  @Path("/issue/message/")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createMessage(String messageJson) {
    return new Response().buildSuccess(communicationMessageFacade.createMessage(messageJson)).toJson();
  }

  @PUT
  @Secured
  @Path("/issue/{id}/reopen/")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateReopen(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.updateReopen(id)).toJson();
  }

  @PUT
  @Secured
  @Path("/issue/{id}/close")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateClose(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.updateClose(id)).toJson();
  }

  @GET
  @Secured
  @Path("/issue/{id}/messages")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageById(@PathParam("id") String id) {
    return new Response().buildSuccess(communicationMessageFacade.getMessageById(id)).toJson();
  }

  @GET
  @Secured
  @Path("/issue/{id}/messages/{limit}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageByIdLimit(@PathParam("id") String id, @PathParam("limit") String limit) {
    return new Response().buildSuccess(communicationMessageFacade.getMessageByIdLimit(id, limit)).toJson();
  }

  @GET
  @Secured
  @Path("/issue/list")
  @Consumes(MediaType.APPLICATION_JSON)
  public String listIssue(@Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

    return new Response().buildSuccess(communicationMessageFacade.listIssue(userEmail)).toJson();
  }

}