package br.org.otus.laboratory.tubes.api;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;

public class LaboratoryTubesFacade {

  @Inject
  private ParticipantLaboratoryService service;

  public ParticipantLaboratory getTubeWithParticipantLaboratory(String tube) {
    try {
      return service.getTubeWithParticipantLaboratory(tube);
    } catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }
}
