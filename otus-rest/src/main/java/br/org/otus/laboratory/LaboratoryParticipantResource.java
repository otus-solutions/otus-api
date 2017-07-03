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

import br.org.otus.laboratory.api.ParticipantLaboratoryFacade;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/laboratory-participant")
public class LaboratoryParticipantResource {

	@Inject
	private ParticipantLaboratoryFacade participantLaboratoryFacade;
	@Inject
	private LaboratoryConfigurationService laboratoryConfigurationService;

	@POST
	@Secured
	@Path("/initialize/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String initialize(@PathParam("rn") Long recruitmentNumber) throws DataNotFoundException {
		ParticipantLaboratory laboratory = null;

		if (participantLaboratoryFacade.hasLaboratory(recruitmentNumber)) {
			laboratory = participantLaboratoryFacade.getLaboratory(recruitmentNumber);
		} else {
			laboratory = participantLaboratoryFacade.create(recruitmentNumber);
		}

		return new Response().buildSuccess(ParticipantLaboratory.serialize(laboratory)).toJson();
	}

	@GET
	@Secured
	@Path("/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getLaboratory(@PathParam("rn") Long recruitmentNumber) throws DataNotFoundException {
		ParticipantLaboratory laboratory = participantLaboratoryFacade.getLaboratory(recruitmentNumber);
		return new Response().buildSuccess(ParticipantLaboratory.serialize(laboratory)).toJson();
	}

	@GET
	@Secured
	@Path("/descriptor")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getDescriptor() {
		LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationService.getLaboratoryConfiguration();
		LaboratoryConfigurationDTO laboratoryConfigurationDTO = new LaboratoryConfigurationDTO(laboratoryConfiguration);
		return new Response().buildSuccess(laboratoryConfigurationDTO).toJson();
	}

	@PUT
	@Secured
	@Path("/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String update(@PathParam("rn") long rn, String participantLaboratory) {
		ParticipantLaboratory deserialized = ParticipantLaboratory.deserialize(participantLaboratory);
		ParticipantLaboratory updatedLaboratory = participantLaboratoryFacade.update(deserialized);
		return new Response().buildSuccess(ParticipantLaboratory.serialize(updatedLaboratory)).toJson();
	}

	@GET
	@Path("/aliquots/{aliquotCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response isAliquoted(@PathParam("aliquotCode") String aliquotCode) {
		return javax.ws.rs.core.Response.ok(participantLaboratoryFacade.checkAliquotIsUnique(aliquotCode)).build();
	}
}
