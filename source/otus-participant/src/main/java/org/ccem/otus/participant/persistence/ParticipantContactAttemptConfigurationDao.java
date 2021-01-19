package org.ccem.otus.participant.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptConfiguration;

public interface ParticipantContactAttemptConfigurationDao {

  ParticipantContactAttemptConfiguration findMetadataAttempt(String objectType) throws DataNotFoundException;

}
