package br.org.otus.importation;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.participant.importation.model.ParticipantImport;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.org.otus.importation.participant.api.ParticipantImportationFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;

@Path("importation/participant")
public class ParticipantImportationResource {

  @Inject
  private ParticipantImportationFacade participantImportationFacade;

  @POST
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String post(String participantImports) {
    Type participantImportSetType = new TypeToken<HashSet<ParticipantImport>>() {}.getType();

    Set<ParticipantImport> participants = new Gson().fromJson(participantImports, participantImportSetType);
    participantImportationFacade.importParticipantSet(participants);
    return new Response().buildSuccess().toJson();
  }
}
