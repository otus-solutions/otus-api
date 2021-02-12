package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
    noteAboutParticipant.setDate(DateUtil.nowToISODate());
    return noteAboutParticipantDao.create(noteAboutParticipant);
  }

  @Override
  public ObjectId update(String userId, NoteAboutParticipant commentAboutParticipant) {
    //check if creator is the user
    return noteAboutParticipantDao.update(commentAboutParticipant);
  }

  @Override
  public void delete(String userId, ObjectId commentAboutParticipantId) throws DataNotFoundException {
    noteAboutParticipantDao.delete(commentAboutParticipantId);
  }

  @Override
  public List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit) {
    return noteAboutParticipantDao.get(recruitmentNumber, skip, limit);
  }

}
