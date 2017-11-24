package br.org.otus.laboratory;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.dto.UpdateTubeCollectionDataDTO;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/laboratory-participant")
public class ParticipantLaboratoryResource {

	@Inject
	private ParticipantLaboratoryFacade participantLaboratoryFacade;

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

	@PUT
	@Secured
	@Path("/tube-collection-data/{rn}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response updateTubeCollectionData(@PathParam("rn") long rn, String updateTubeCollectionDataDTO) {
		UpdateTubeCollectionDataDTO updateTubes = UpdateTubeCollectionDataDTO.deserialize(updateTubeCollectionDataDTO);
		for (Tube tube : updateTubes.getTubes()) {
			participantLaboratoryFacade.updateTubeCollectionData(rn,tube);
		}
		return javax.ws.rs.core.Response.ok().build();
	}

	@PUT
	@Path("/{rn}/tubes/aliquots")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response updateAliquots(@PathParam("rn") long rn, String updateAliquotsDTO) {
		UpdateAliquotsDTO updateAliquots = UpdateAliquotsDTO.deserialize(updateAliquotsDTO);
		updateAliquots.setRecruitmentNumber(rn);
		participantLaboratoryFacade.updateAliquotList(updateAliquots);
		return javax.ws.rs.core.Response.ok().build();
	}

}
