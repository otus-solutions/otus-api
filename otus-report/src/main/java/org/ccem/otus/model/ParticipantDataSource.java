package org.ccem.otus.model;

import org.ccem.otus.persistence.ParticipantDataSourceDao;

import javax.inject.Inject;

public class ParticipantDataSource extends DataSource {
    @Inject
    private ParticipantDataSourceDao ParticipantDataSourceDao;

    private String rn;
    private String birthdate;
}
