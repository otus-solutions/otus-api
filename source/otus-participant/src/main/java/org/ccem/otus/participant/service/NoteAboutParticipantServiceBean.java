package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
    if(noteAboutParticipant.getId() == null){
      throw new DataNotFoundException("Field id is missing");
    }

    if(noteAboutParticipant.getUserId() == null){
      noteAboutParticipant.setUserId(userOid);
    }
    else if(!noteAboutParticipant.getUserId().equals(userOid)){
      throw new ValidationException();
    }

    noteAboutParticipant.setLastUpdate(DateUtil.nowToISODate());
    noteAboutParticipant.setEdited(true);
    noteAboutParticipantDao.update(noteAboutParticipant);
  }

  @Override
  public void delete(ObjectId userOid, ObjectId noteAboutParticipantOid) throws DataNotFoundException, ValidationException {
    noteAboutParticipantDao.delete(userOid, noteAboutParticipantOid);
  }

  @Override
  public List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit) {
    return noteAboutParticipantDao.get(recruitmentNumber, skip, limit);
  }

}
