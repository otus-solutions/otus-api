package br.org.otus.participant.api;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAddressAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptConfiguration;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptExtractionDTO;
import org.ccem.otus.participant.model.participant_contact.Address;
import org.ccem.otus.participant.service.ParticipantContactAttemptService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class ParticipantContactAttemptFacade {

  @Inject
  private ParticipantContactAttemptService participantContactAttemptService;

  public String create(String participantContactAttemptJson, String userEmail){
    try{
      return participantContactAttemptService.create(ParticipantContactAttempt.deserialize(participantContactAttemptJson), userEmail).toString();
    }
    catch (DataFormatException | DataNotFoundException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }
  public void updateAttemptAddress(Long recruitmentNumber, String objectType, String position, String addressJson){
    try{
      participantContactAttemptService.updateAttemptAddress(recruitmentNumber, objectType, position, Address.deserialize(addressJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }
  public void changeAddress(Long recruitmentNumber, String objectType, String position){
    try{
      participantContactAttemptService.changeAddress(recruitmentNumber, objectType, position);
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }
  public void delete(String participantContactAttemptID) {
    try {
      participantContactAttemptService.delete(new ObjectId(participantContactAttemptID));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public ArrayList<ParticipantContactAddressAttempt> findAddressAttempts(Long recruitmentNumber, String objectType, String position) {
    try{
      return participantContactAttemptService.findAddressAttempts(recruitmentNumber, objectType, position);
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public ParticipantContactAttemptConfiguration findMetadataAttempt(String objectType) {
    try {
      return participantContactAttemptService.findMetadataAttempt(objectType);
    }catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public ArrayList<ParticipantContactAttemptExtractionDTO> finParticipantContactAttempts() {
    try{
      return participantContactAttemptService.finParticipantContactAttempts();
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

}
