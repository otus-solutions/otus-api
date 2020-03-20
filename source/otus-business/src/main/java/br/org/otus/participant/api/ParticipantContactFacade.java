package br.org.otus.participant.api;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;
import org.ccem.otus.participant.service.ParticipantContactService;

import javax.inject.Inject;
import java.util.zip.DataFormatException;

public class ParticipantContactFacade {

  @Inject
  private ParticipantContactService participantContactService;

  public String create(String participantContactJson){
    return participantContactService.create(ParticipantContact.deserialize(participantContactJson)).toString();
  }

  public void updateEmail(String participantContactDtoJson) {
    try{
      participantContactService.updateEmail(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void updateAddress(String participantContactDtoJson) {
    try{
      participantContactService.updateAddress(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void updatePhoneNumber(String participantContactDtoJson) {
    try{
      participantContactService.updatePhoneNumber(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void addNonMainContact(String participantContactDtoJson) {
    try{
      participantContactService.addNonMainContact(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void swapMainContactWithSecondary(String participantContactDtoJson) {
    try{
      participantContactService.swapMainContactWithSecondary(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
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

  public void deleteNonMainContact(String participantContactDtoJson) {
    try{
      participantContactService.deleteNonMainContact(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
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

  public ParticipantContact getByRecruitmentNumber(String recruitmentNumber) {
    try{
      return participantContactService.getByRecruitmentNumber(Long.parseLong(recruitmentNumber));
    }
    catch (DataNotFoundException | NumberFormatException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }
}
