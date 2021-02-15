package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;
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
      checkInvalidAccessAttempt(userOid, noteAboutParticipant.getId());
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
      checkInvalidAccessAttempt(userOid, noteAboutParticipantOid);
      throw e;
    }
  }

  @Override
  public void delete(ObjectId userOid, ObjectId noteAboutParticipantOid) throws ValidationException, DataNotFoundException {
    try{
      noteAboutParticipantDao.delete(userOid, noteAboutParticipantOid);
    }
    catch (DataNotFoundException e){
      checkInvalidAccessAttempt(userOid, noteAboutParticipantOid);
      throw e;
    }
  }

  @Override
  public List<NoteAboutParticipantDto> getAll(ObjectId userOid, Long recruitmentNumber, int skip, int limit) throws MemoryExcededException {
    return noteAboutParticipantDao.getAll(userOid, recruitmentNumber, skip, limit);
  }

  private void checkInvalidAccessAttempt(ObjectId userOid, ObjectId noteAboutParticipantOid) throws ValidationException {
    NoteAboutParticipant noteAboutParticipantFounded =  noteAboutParticipantDao.get(noteAboutParticipantOid);
    if(noteAboutParticipantFounded != null && !noteAboutParticipantFounded.getUserId().equals(userOid)){
      throw new ValidationException();
    }
  }

}
