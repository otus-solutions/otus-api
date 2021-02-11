package org.ccem.otus.participant.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;

import java.util.List;

public interface NoteAboutParticipantDao {

  ObjectId create(NoteAboutParticipant commentAboutParticipant);

  ObjectId update(NoteAboutParticipant commentAboutParticipant);

  void delete(ObjectId commentAboutParticipantId) throws DataNotFoundException;

  List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit);
}
