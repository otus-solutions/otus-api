package org.ccem.otus.service;

import org.ccem.otus.enums.DataSourceMapping;
import org.ccem.otus.model.ReportDataSource;
import org.ccem.otus.model.ParticipantDataSource;
import org.ccem.otus.persistence.ParticipantDataSourceDao;

import javax.inject.Inject;

public class DataSourceBuilder {

    @Inject
    private ParticipantDataSourceDao participantDataSourceDao;

    public ReportDataSource build(Long recruitmentNumber, ParticipantDataSource participantDataSource){
         return null;
    }

}
