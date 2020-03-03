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

  public void update(String participantContactJson) {
    try{
      participantContactService.update(ParticipantContact.deserialize(participantContactJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void updateMainContact(String participantContactDtoJson) {
    try{
      participantContactService.updateMainContact(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void addSecondaryContact(String participantContactDtoJson) {
    try{
      participantContactService.addSecondaryContact(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException | DataFormatException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void updateSecondaryContact(String participantContactDtoJson) {
    try{
      participantContactService.updateSecondaryContact(ParticipantContactDto.deserialize(participantContactDtoJson));
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

  public void deleteSecondaryContact(String participantContactDtoJson) {
    try{
      participantContactService.deleteSecondaryContact(ParticipantContactDto.deserialize(participantContactDtoJson));
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
