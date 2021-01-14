package org.ccem.otus.participant.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAddressAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

public interface ParticipantContactAttemptDao {

  ObjectId create(ParticipantContactAttempt participantContactAttempt) throws DataFormatException;

  void delete(ObjectId participantContactAttemptOID) throws DataNotFoundException;

  ArrayList<ParticipantContactAddressAttempt> findAddressAttempts(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException;

}