package br.org.otus.laboratory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/laboratory-participant")
public class LaboratoryParticipantResource {

	@Inject
	private ParticipantLaboratoryService laboratoryParticipantService;

	@POST
	@Secured
	@Path("/initialize/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String initialize(@PathParam("rn") Long recruitmentNumber) throws DataNotFoundException {
		ParticipantLaboratory laboratory = null;

		if (laboratoryParticipantService.hasLaboratory(recruitmentNumber)) {
			laboratory = laboratoryParticipantService.getLaboratory(recruitmentNumber);
		} else {
			laboratory = laboratoryParticipantService.create(recruitmentNumber);
		}

		return new Response().buildSuccess(laboratory).toJson();
	}

	@GET
	@Secured
	@Path("/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getLaboratory(@PathParam("rn") Long recruitmentNumber) throws DataNotFoundException {
		ParticipantLaboratory laboratory = laboratoryParticipantService.getLaboratory(recruitmentNumber);
		return new Response().buildSuccess(laboratory).toJson();
	}

}
