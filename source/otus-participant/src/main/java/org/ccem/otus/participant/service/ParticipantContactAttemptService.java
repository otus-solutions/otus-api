package org.ccem.otus.participant.service;

import com.sun.org.apache.bcel.internal.generic.ObjectType;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.MetadataAttemptStatus;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

public interface ParticipantContactAttemptService {

  ObjectId create(ParticipantContactAttempt participantContact) throws DataFormatException;

  void delete(ObjectId participantContactAttemptOID) throws DataNotFoundException;

  ArrayList<ParticipantContactAttempt> findAttempts(Long recruitmentNumber) throws DataNotFoundException;
  MetadataAttemptStatus findMetadataAttempt(String objectType) throws DataNotFoundException;
}
