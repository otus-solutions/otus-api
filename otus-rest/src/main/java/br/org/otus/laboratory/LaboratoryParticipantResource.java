package br.org.otus.laboratory;

import br.org.otus.laboratory.participant.LaboratoryParticipant;
import br.org.otus.laboratory.service.LaboratoryParticipantService;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
