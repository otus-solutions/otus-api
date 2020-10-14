package br.org.otus.laboratory;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.tubes.api.LaboratoryTubesFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/laboratory-tubes")
public class LaboratoryTubesResource {
  @Inject
  private LaboratoryTubesFacade laboratoryTubesFacade;

  @GET
  @Secured
  @Path("/{tubeCode}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getTubeWithParticipant(@PathParam("tubeCode") String tubeCode) throws DataNotFoundException {
    ParticipantLaboratory participantTube = laboratoryTubesFacade.getTubeWithParticipantLaboratory(tubeCode);
    return new Response().buildSuccess(ParticipantLaboratory.serialize(participantTube)).toJson();
  }

}
