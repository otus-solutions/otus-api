package org.ccem.otus.service;

import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.persistence.ParticipantDataSourceDao;

import javax.inject.Inject;

public class DataSourceBuilder {

    @Inject
    private ParticipantDataSourceDao participantDataSourceDao;

    public ReportDataSource build(Long recruitmentNumber, ParticipantDataSource participantDataSource){
         return null;
    }

}
