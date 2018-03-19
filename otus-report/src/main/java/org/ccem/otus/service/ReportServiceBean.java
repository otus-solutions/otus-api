package org.ccem.otus.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
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
    public ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException {
        ObjectId reportObjectId = new ObjectId(reportId);
        ReportTemplate report = reportDao.findReport(reportObjectId);
        for (ReportDataSource dataSource:report.getDataSources()) {
            if(dataSource instanceof ParticipantDataSource){
                ((ParticipantDataSource) dataSource).getResult().add(participantDataSourceDao.getResult(recruitmentNumber,(ParticipantDataSource) dataSource));
            }else if(dataSource instanceof ActivityDataSource){
                ((ActivityDataSource) dataSource).getResult().add(activityDataSourceDao.getResult(recruitmentNumber,(ActivityDataSource) dataSource));
            }
        }
        return report;
    }
}
