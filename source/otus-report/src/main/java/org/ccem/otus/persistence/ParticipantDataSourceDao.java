package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSourceResult;

public interface ParticipantDataSourceDao {

  ParticipantDataSourceResult getResult(Long recruitmentNumber, ParticipantDataSource participantDataSource) throws DataNotFoundException;

}
