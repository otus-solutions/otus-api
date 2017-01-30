package br.org.otus.participant;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.Participant;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.security.Secured;

@Path("/participants")
public class ParticipantResource {

	@Inject
	private ParticipantFacade participantFacade;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Participant> getAll() {
		return participantFacade.listAll();
	}
	
	@GET
	@Path("/{rn}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Participant getByRecruitmentNumber(@PathParam("rn") long rn) {
		return participantFacade.getByRecruitmentNumber(rn);
	}

}
