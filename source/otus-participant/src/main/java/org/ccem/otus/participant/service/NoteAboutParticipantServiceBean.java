package org.ccem.otus.participant.service;

import br.org.otus.model.User;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;
import org.ccem.otus.participant.persistence.NoteAboutParticipantDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NoteAboutParticipantServiceBean implements NoteAboutParticipantService {

  @Inject
  private NoteAboutParticipantDao commentAboutParticipantDao;

  @Override
  public ObjectId create(Long recruitmentNumber, String comment) {
    NoteAboutParticipant commentAboutParticipant = new NoteAboutParticipant();
    return commentAboutParticipantDao.create(commentAboutParticipant);
  }

  @Override
  public ObjectId update(String userId, NoteAboutParticipant commentAboutParticipant) {
    //check if creator is the user
    return commentAboutParticipantDao.update(commentAboutParticipant);
  }

  @Override
  public void delete(String userId, ObjectId commentAboutParticipantId) throws DataNotFoundException {
    commentAboutParticipantDao.delete(commentAboutParticipantId);
  }

  @Override
  public List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit) {
    return commentAboutParticipantDao.get(recruitmentNumber, skip, limit);
  }

}
