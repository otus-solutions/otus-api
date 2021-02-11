package br.org.otus.participant;

import br.org.otus.UserAuthenticationResource;
import br.org.otus.participant.api.NoteAboutParticipantFacade;
import br.org.otus.rest.Response;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("participant/note-about")
public class NoteAboutParticipantResource extends UserAuthenticationResource {

  @Inject
  private NoteAboutParticipantFacade noteAboutParticipantFacade;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(@Context HttpServletRequest request, String noteAboutParticipantJson){
    String id = noteAboutParticipantFacade.create(getUser(request), noteAboutParticipantJson);
    return new Response().buildSuccess(id).toJson();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(@Context HttpServletRequest request, String noteAboutParticipantJson){
    noteAboutParticipantFacade.update(getUser(request), noteAboutParticipantJson);
    return new Response().buildSuccess().toJson();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public String delete(@Context HttpServletRequest request, String noteAboutParticipantId){
    noteAboutParticipantFacade.delete(getUser(request), noteAboutParticipantId);
    return new Response().buildSuccess().toJson();
  }

}