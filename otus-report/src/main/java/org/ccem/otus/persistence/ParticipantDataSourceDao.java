package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.model.dataSources.ParticipantDataSourceResult;

public interface ParticipantDataSourceDao{

    ParticipantDataSourceResult getResult(long recruitmentNumber, ParticipantDataSource participantDataSource) throws DataNotFoundException;

}
