package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;

import java.util.List;

public interface NoteAboutParticipantService {

  ObjectId create(Long recruitmentNumber, String comment);

  ObjectId update(String userId, NoteAboutParticipant commentAboutParticipant);

  void delete(String userId, ObjectId commentAboutParticipantId) throws DataNotFoundException;

  List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit);
}
