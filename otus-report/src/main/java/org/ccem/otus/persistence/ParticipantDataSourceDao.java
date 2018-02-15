package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ParticipantDataSource;
import org.ccem.otus.model.ParticipantDataSourceResult;

public interface ParticipantDataSourceDao{

    ParticipantDataSourceResult getResult(long recruitmentNumber, ParticipantDataSource participantDataSource) throws DataNotFoundException;

}
