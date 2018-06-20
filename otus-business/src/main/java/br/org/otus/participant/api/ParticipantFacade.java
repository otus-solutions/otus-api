package br.org.otus.participant.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Monitoring;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class ParticipantFacade {

  @Inject
  private ParticipantService participantService;

  public Participant getByRecruitmentNumber(long rn) {
    try {
      return participantService.getByRecruitmentNumber(rn);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));

    }
  }

  public List<Participant> list(FieldCenter fieldCenter) {
    return participantService.list(fieldCenter);
  }

  public List<Monitoring> getGoalsByCenter() {
    try {
      return participantService.getGoalsByCenter();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

}
