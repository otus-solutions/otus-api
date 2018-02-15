package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ParticipantDataSource;
import org.ccem.otus.model.ParticipantDataSourceResult;
import org.ccem.otus.model.ReportDataSource;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReportServiceBean implements ReportService {

    @Inject
    private ReportDao reportDao;

    @Inject
    private ParticipantDataSourceDao participantDataSourceDao;

    @Override
    public ReportTemplate findReport(long ri, long rn) throws DataNotFoundException{
        ReportTemplate report = reportDao.findReport(ri);
        for (ReportDataSource dataSource:report.getDataSources()) {
            if(dataSource instanceof ParticipantDataSource){
                dataSource.addResult(participantDataSourceDao.getResult(rn,(ParticipantDataSource) dataSource));
            }
        }
        return report;
    }
}
