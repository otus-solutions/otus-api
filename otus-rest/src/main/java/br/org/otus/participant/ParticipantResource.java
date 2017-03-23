package br.org.otus.participant;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.security.Secured;

import com.google.gson.GsonBuilder;

@Path("/participants")
public class ParticipantResource {

	@Inject
	private ParticipantFacade participantFacade;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {
		// TODO: it needs to use Response.toJson() - reminder: data:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
		return builder.create().toJson(participantFacade.listAll());
	}

	@GET
	@Path("/{rn}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Participant getByRecruitmentNumber(@PathParam("rn") long rn) {
		return participantFacade.getByRecruitmentNumber(rn);
	}

}
