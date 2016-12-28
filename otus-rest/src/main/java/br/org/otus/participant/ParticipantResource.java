package br.org.otus.participant;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.Participant;
import org.ccem.otus.service.ParticipantService;

import br.org.otus.security.Secured;

@Path("/participants")
public class ParticipantResource {

	@Inject
	private ParticipantService participantService;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Participant> getAll() {
		return participantService.list();
	}

}
