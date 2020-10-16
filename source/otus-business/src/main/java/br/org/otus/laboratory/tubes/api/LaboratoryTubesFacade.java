package br.org.otus.laboratory.tubes.api;

import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.laboratory.participant.TubeParticipantLaboratory;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;

public class LaboratoryTubesFacade {

  @Inject
  private ParticipantLaboratoryService service;

  public TubeParticipantLaboratory getTubeWithRn(String tube) {
    try {
      return service.getTubeWithRn(tube);
    } catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }
}
