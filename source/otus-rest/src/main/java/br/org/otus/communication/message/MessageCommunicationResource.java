package br.org.otus.communication.message;

import br.org.otus.communication.IssueMessageDTO;
import br.org.otus.communication.MessageCommunicationFacade;
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

@Path("/project-communication")
public class MessageCommunicationResource {

  @Inject
  private MessageCommunicationFacade messageCommunicationFacade;

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

    return new Response().buildSuccess(messageCommunicationFacade.createIssue(userEmail, messageJson)).toJson();
  }

  @POST
  @Secured
  @Path("/issue/message/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createMessage(@PathParam("id") String id, String messageJson) {
    return new Response().buildSuccess(messageCommunicationFacade.createMessage(id, messageJson)).toJson();
  }

  @PUT
  @Secured
  @Path("/issue/{id}/reopen")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateReopen(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.updateReopen(id)).toJson();
  }

  @PUT
  @Secured
  @Path("/issue/{id}/close")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateClose(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.updateClose(id)).toJson();
  }

  @GET
  @Secured
  @Path("/issue/{id}/messages")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageById(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.getMessageById(id)).toJson();
  }

  @GET
  @Secured
  @Path("/issue/{id}/messages/{limit}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageByIdLimit(@PathParam("id") String id, @PathParam("limit") String limit) {
    return new Response().buildSuccess(messageCommunicationFacade.getMessageByIdLimit(id, limit)).toJson();
  }

  @GET
  @Secured
  @Path("/issue/list")
  @Consumes(MediaType.APPLICATION_JSON)
  public String listIssue(@Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

    return new Response().buildSuccess(messageCommunicationFacade.listIssue(userEmail)).toJson();
  }

}