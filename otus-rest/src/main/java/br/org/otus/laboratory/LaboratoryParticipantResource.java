package br.org.otus.laboratory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.Participant;

import br.org.otus.laboratory.participant.LaboratoryParticipant;
import br.org.otus.laboratory.service.LaboratoryParticipantService;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/laboratory-participant")
public class LaboratoryParticipantResource {

	@Inject
	private LaboratoryParticipantService laboratoryParticipantService;

	@POST
	@Secured
	@Path("/generate/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String create(@PathParam("rn") Long rn) throws DataNotFoundException {
		LaboratoryParticipant labParticipant = null;

		if (!laboratoryParticipantService.hasLaboratory(rn)) {
			labParticipant = laboratoryParticipantService.create(rn);
		} else {
			labParticipant = laboratoryParticipantService.getLaboratory(rn);
		}

		return new Response().buildSuccess(labParticipant).toJson();
	}

	@POST
	@Secured
	@Path("/generate-empty/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createEmpty(@PathParam("rn") Long rn) throws DataNotFoundException {
		LaboratoryParticipant labParticipant = null;

		if (!laboratoryParticipantService.hasLaboratory(rn)) {
			labParticipant = laboratoryParticipantService.createEmptyLaboratory(rn);
		} else {
			labParticipant = laboratoryParticipantService.getLaboratory(rn);
		}

		return new Response().buildSuccess(labParticipant).toJson();
	}

	@PUT
	@Secured
	@Path("/generate-tubes/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String generateTubes(@PathParam("rn") Long rn) throws DataNotFoundException {
		LaboratoryParticipant labParticipant = laboratoryParticipantService.addTubesToParticipant(rn);
		return new Response().buildSuccess(labParticipant).toJson();
	}

	@GET
	@Secured
	@Path("/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getLaboratory(@PathParam("rn") Long rn) throws DataNotFoundException {
		LaboratoryParticipant laboratoryParticipant = laboratoryParticipantService.getLaboratory(rn);
		return new Response().buildSuccess(laboratoryParticipant).toJson();
	}

}
