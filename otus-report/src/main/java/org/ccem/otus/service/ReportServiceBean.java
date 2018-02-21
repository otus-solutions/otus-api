package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.*;
import org.ccem.otus.model.dataSources.ActivityDataSource;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.persistence.ActivityDataSourceDao;
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

    @Inject
    private ActivityDataSourceDao activityDataSourceDao;

    @Override
    public ReportTemplate findReportById(RequestParameters requestParameters) throws DataNotFoundException{
        ReportTemplate report = reportDao.findReport(requestParameters.getReportId());
        for (ReportDataSource dataSource:report.getDataSources()) {
            if(dataSource instanceof ParticipantDataSource){
                dataSource.addResult(participantDataSourceDao.getResult(requestParameters.getRecruitmentNumber(),(ParticipantDataSource) dataSource));
            }
            if(dataSource instanceof ActivityDataSource){
                dataSource.addResult(activityDataSourceDao.getResult(requestParameters.getRecruitmentNumber(),(ActivityDataSource) dataSource));
            }
        }
        return report;
    }
}
