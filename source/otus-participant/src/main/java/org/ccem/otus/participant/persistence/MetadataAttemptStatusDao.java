package org.ccem.otus.participant.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.MetadataAttemptStatus;

public interface MetadataAttemptStatusDao {

  MetadataAttemptStatus findMetadataAttempt(String objectType) throws DataNotFoundException;

}
