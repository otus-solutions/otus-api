package br.org.otus.participant.api;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

  public Long getPartipantsActives(String acronymCenter) {
    try {
      return participantService.getPartipantsActives(acronymCenter);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public Participant create(String participantJson) {
    Participant insertedParticipant = null;
    try {
      Participant participant = Participant.deserialize(participantJson);
      insertedParticipant = participantService.create(participant);
      return insertedParticipant;
    } catch (ValidationException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }

  }

  public ArrayList<Long> listCenterRecruitmentNumbers(String center) {
    try {
      return participantService.listCenterRecruitmentNumbers(center);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

}
