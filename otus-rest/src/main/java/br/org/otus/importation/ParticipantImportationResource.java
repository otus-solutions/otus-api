package br.org.otus.importation;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.participant.importation.model.ParticipantImport;

import br.org.otus.importation.participant.api.ParticipantImportationFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("importation/participant")
public class ParticipantImportationResource {

	@Inject
	private ParticipantImportationFacade participantImportationFacade;

	@POST
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String post(Set<ParticipantImport> participantImports) {
		participantImportationFacade.importParticipantSet(participantImports);
		return new Response().buildSuccess().toJson();
	}
}
