package br.org.otus.laboratory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.dto.UpdateTubeCollectionDataDTO;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/laboratory-participant")
public class ParticipantLaboratoryResource {

    @Inject
    private ParticipantLaboratoryFacade participantLaboratoryFacade;

    @POST
    @Secured
    @Path("/initialize/{rn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized String initialize(@PathParam("rn") Long recruitmentNumber) throws DataNotFoundException {
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
    @Path("/tube-collection-data/{rn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response updateTubeCollectionData(@PathParam("rn") long rn, String updateTubeCollectionDataDTO) {
        UpdateTubeCollectionDataDTO updateTubes = UpdateTubeCollectionDataDTO.deserialize(updateTubeCollectionDataDTO);
        for (Tube tube : updateTubes.getTubes()) {
            participantLaboratoryFacade.updateTubeCollectionData(rn, tube);
        }
        return javax.ws.rs.core.Response.ok().build();
    }

    @PUT
    @Secured
    @Path("/{rn}/tubes/aliquots")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response updateAliquots(@PathParam("rn") long rn, String updateAliquotsDTO) {
        UpdateAliquotsDTO updateAliquots = UpdateAliquotsDTO.deserialize(updateAliquotsDTO);
        updateAliquots.setRecruitmentNumber(rn);
        participantLaboratoryFacade.updateAliquotList(updateAliquots);
        return javax.ws.rs.core.Response.ok().build();
    }

    @DELETE
    @Secured
    @Path("/aliquot/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAliquot(@PathParam("code") String code) {
        participantLaboratoryFacade.deleteAliquot(code);
        return new Response().buildSuccess().toJson();
    }

    @PUT
    @Secured
    @Path("/convert-aliquot-role")
    public javax.ws.rs.core.Response convertAliquotRole(String convertedAliquotJson) {
        Aliquot convertedAliquot = Aliquot.deserialize(convertedAliquotJson);
        return javax.ws.rs.core.Response.ok(participantLaboratoryFacade.convertAliquotRole(convertedAliquot)).build();
    }
}