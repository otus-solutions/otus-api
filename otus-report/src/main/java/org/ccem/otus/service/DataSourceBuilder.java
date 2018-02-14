package org.ccem.otus.service;

import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.ParticipantDataSource;
import org.ccem.otus.persistence.ParticipantDataSourceDao;

import javax.inject.Inject;

public class DataSourceBuilder {
    @Inject
    private ParticipantDataSourceDao participantDataSourceDao;

    public DataSource build(ParticipantDataSource participantDataSource){
        return null;
        // return participantDataSourceDao.getResult(participantDataSource)
    }

}
