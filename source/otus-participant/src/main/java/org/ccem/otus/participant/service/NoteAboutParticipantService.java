package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;

import java.util.List;

public interface NoteAboutParticipantService {

  ObjectId create(ObjectId userOid, NoteAboutParticipant noteAboutParticipant);

  ObjectId update(ObjectId userOid, NoteAboutParticipant noteAboutParticipant);

  void delete(ObjectId userOid, ObjectId noteAboutParticipantOid) throws DataNotFoundException, ValidationException;

  List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit);
}
