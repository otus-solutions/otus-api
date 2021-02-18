package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipant;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipantResponse;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipantSearchSettingsDto;
import org.ccem.otus.participant.persistence.NoteAboutParticipantDao;
import org.ccem.otus.utils.DateUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NoteAboutParticipantServiceBean implements NoteAboutParticipantService {

  @Inject
  private NoteAboutParticipantDao noteAboutParticipantDao;

  @Override
  public ObjectId create(ObjectId userOid, NoteAboutParticipant noteAboutParticipant) {
    noteAboutParticipant.setUserId(userOid);
    noteAboutParticipant.setCreationDate(DateUtil.nowToISODate());
    return noteAboutParticipantDao.create(noteAboutParticipant);
  }

  @Override
  public void update(ObjectId userOid, NoteAboutParticipant noteAboutParticipant) throws ValidationException, DataNotFoundException {
    noteAboutParticipant.setLastUpdate(DateUtil.nowToISODate());
    noteAboutParticipant.setEdited(true);
    try{
      noteAboutParticipantDao.update(userOid, noteAboutParticipant);
    }
    catch (DataNotFoundException e){
      checkNoteExistenceOnlyById(noteAboutParticipant.getId());
      throw e;
    }
  }

  @Override
  public void updateStarred(ObjectId userOid, ObjectId noteAboutParticipantOid, Boolean starred) throws ValidationException, DataNotFoundException {
    if(starred == null){
      throw new ValidationException("Missing starred field");
    }
    try{
      noteAboutParticipantDao.updateStarred(userOid, noteAboutParticipantOid, starred);
    }
    catch (DataNotFoundException e){
      checkNoteExistenceOnlyById(noteAboutParticipantOid);
      throw e;
    }
  }

  @Override
  public void delete(ObjectId userOid, ObjectId noteAboutParticipantOid) throws ValidationException, DataNotFoundException {
    try{
      noteAboutParticipantDao.delete(userOid, noteAboutParticipantOid);
    }
    catch (DataNotFoundException e){
      checkNoteExistenceOnlyById(noteAboutParticipantOid);
      throw e;
    }
  }

  @Override
  public List<NoteAboutParticipantResponse> getAll(ObjectId userOid, Long recruitmentNumber, NoteAboutParticipantSearchSettingsDto searchSettingsDto) throws MemoryExcededException, DataNotFoundException, ValidationException {
    if(!searchSettingsDto.isValid()){
      throw new ValidationException("Search settings dto is not valid");
    }
    return noteAboutParticipantDao.getAll(userOid, recruitmentNumber, searchSettingsDto);
  }

  private void checkNoteExistenceOnlyById(ObjectId noteAboutParticipantOid) throws ValidationException {
    NoteAboutParticipant noteAboutParticipantFound = noteAboutParticipantDao.get(noteAboutParticipantOid);
    if(noteAboutParticipantFound != null){
      throw new ValidationException();
    }
  }

}
