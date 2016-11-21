package br.org.otus.importation;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.importation.service.ParticipantImportService;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("importation/participant")
public class ParticipantResource {

	@Inject
	private ParticipantImportService participantImportService;

	@POST
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String post(Set<ParticipantImport> participantImports) {
		participantImportService.importation(participantImports);
		return new Response().buildSuccess().toJson();
	}
}
