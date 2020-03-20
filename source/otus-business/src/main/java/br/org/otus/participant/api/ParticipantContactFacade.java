package br.org.otus.participant.api;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;
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
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (DataFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void updateAddress(String participantContactDtoJson) {
    try{
      participantContactService.updateAddress(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (DataFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void updatePhoneNumber(String participantContactDtoJson) {
    try{
      participantContactService.updatePhoneNumber(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (DataFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void addNonMainEmail(String participantContactDtoJson) {
    try{
      participantContactService.addNonMainEmail(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (DataFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void addNonMainAddress(String participantContactDtoJson) {
    try{
      participantContactService.addNonMainAddress(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (DataFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void addNonMainPhoneNumber(String participantContactDtoJson) {
    try{
      participantContactService.addNonMainPhoneNumber(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (DataFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void swapMainContactWithSecondary(String participantContactDtoJson) {
    try{
      participantContactService.swapMainContactWithSecondary(ParticipantContactDto.deserialize(participantContactDtoJson));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (DataFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
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

  public ParticipantContact getParticipantContact(String participantContactID) {
    try{
      return participantContactService.getParticipantContact(new ObjectId(participantContactID));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public ParticipantContact getParticipantContactByRecruitmentNumber(String recruitmentNumber) {
    try{
      return participantContactService.getParticipantContactByRecruitmentNumber(Long.parseLong(recruitmentNumber));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
    catch (NumberFormatException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }
}
