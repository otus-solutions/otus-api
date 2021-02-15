package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantResponse;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantSearchSettingsDto;

import java.util.List;

public interface NoteAboutParticipantService {

  ObjectId create(ObjectId userOid, NoteAboutParticipant noteAboutParticipant);

  void update(ObjectId userOid, NoteAboutParticipant noteAboutParticipant) throws ValidationException, DataNotFoundException;

  void updateStarred(ObjectId userOid, ObjectId noteAboutParticipantOid, Boolean starred) throws ValidationException, DataNotFoundException;

  void delete(ObjectId userOid, ObjectId noteAboutParticipantOid) throws ValidationException, DataNotFoundException;

  List<NoteAboutParticipantResponse> getAll(ObjectId userOid, Long recruitmentNumber, NoteAboutParticipantSearchSettingsDto searchSettingsDto) throws MemoryExcededException;
}
