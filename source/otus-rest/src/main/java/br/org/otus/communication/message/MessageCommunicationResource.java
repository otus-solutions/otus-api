package br.org.otus.communication.message;

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
  @Path("/issues")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createIssue(@Context HttpServletRequest request, String issueJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);

    return new Response().buildSuccess(messageCommunicationFacade.createIssue(token, issueJson)).toJson();
  }

  @GET
  @Secured
  @Path("/issues")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String fetchIssues(@Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);

    return new Response().buildSuccess(messageCommunicationFacade.getIssue(token)).toJson();
  }

  @POST
  @Secured
  @Path("/issues/filter")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String filter(String filterJson) {
    return new Response().buildSuccess(messageCommunicationFacade.filter(filterJson)).toJson();
  }

  @PUT
  @Secured
  @Path("/issues/{id}/reopen")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateReopen(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.updateReopen(id)).toJson();
  }

  @PUT
  @Secured
  @Path("/issues/{id}/close")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateClose(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.updateClose(id)).toJson();
  }

  @PUT
  @Secured
  @Path("/issues/{id}/finalize")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateFinalize(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.updateFinalize(id)).toJson();
  }

  @GET
  @Secured
  @Path("/senders/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getSenderById(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.getSenderById(id)).toJsonWithStringOid();
  }

  @POST
  @Secured
  @Path("/issues/{id}/messages")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createMessage(@Context HttpServletRequest request, @PathParam("id") String id, String messageJson) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));

    return new Response().buildSuccess(messageCommunicationFacade.createMessage(token, id, messageJson)).toJson();
  }

  @GET
  @Secured
  @Path("/issues/{issueId}/messages")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageById(@PathParam("issueId") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.getMessageByIssueId(id)).toJson();
  }

  @GET
  @Secured
  @Path("/issues/{issueId}/messages/{skip}/{limit}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getMessageByIdLimit(@PathParam("issueId") String id, @PathParam("skip") String skip, @PathParam("limit") String limit) {
    return new Response().buildSuccess(messageCommunicationFacade.getMessageByIssueIdLimit(id, skip, limit)).toJson();
  }

  @GET
  @Secured
  @Path("/issues/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getIssuesById(@PathParam("id") String id) {
    return new Response().buildSuccess(messageCommunicationFacade.getIssueById(id)).toJson();
  }

}
