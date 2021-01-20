package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAddressAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptConfiguration;
import org.ccem.otus.participant.model.participant_contact.Address;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

public interface ParticipantContactAttemptService {

  ObjectId create(ParticipantContactAttempt participantContact, String userEmail) throws DataFormatException, DataNotFoundException;

  void delete(ObjectId participantContactAttemptOID) throws DataNotFoundException;

  void updateAttemptAddress(Long recruitmentNumber, String objectType, String position, Address addresses) throws DataNotFoundException;

  void changeAddress(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException;

  ArrayList<ParticipantContactAddressAttempt> findAddressAttempts(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException;

  ParticipantContactAttemptConfiguration findMetadataAttempt(String objectType) throws DataNotFoundException;

}
