package br.org.otus.participant.api;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.ParticipantContact;
import org.ccem.otus.participant.service.ParticipantContactService;

import javax.inject.Inject;

public class ParticipantContactFacade {

  @Inject
  private ParticipantContactService participantContactService;

  public String create(String participantContactJson){
    return participantContactService.create(ParticipantContact.deserialize(participantContactJson)).toString();
  }

  public void update(String participantContactID, String participantContactJson) {
    try{
      participantContactService.update(new ObjectId(participantContactID), ParticipantContact.deserialize(participantContactJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void delete(String participantContactID) {
    try {
      participantContactService.delete(new ObjectId(participantContactID));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public ParticipantContact get(String participantContactID) {
    try{
      return participantContactService.get(new ObjectId(participantContactID));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }
}
