package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptConfiguration;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

public interface ParticipantContactAttemptService {

  ObjectId create(ParticipantContactAttempt participantContact) throws DataFormatException;

  void delete(ObjectId participantContactAttemptOID) throws DataNotFoundException;

  ArrayList<ParticipantContactAttempt> findAttempts(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException;

  ParticipantContactAttemptConfiguration findMetadataAttempt(String objectType) throws DataNotFoundException;

}
