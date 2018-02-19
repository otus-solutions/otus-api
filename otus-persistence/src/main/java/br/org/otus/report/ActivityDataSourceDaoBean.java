package br.org.otus.report;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.dataSources.ActivityDataSource;
import org.ccem.otus.model.dataSources.ActivityDataSourceResult;
import org.ccem.otus.persistence.ActivityDataSourceDao;

public class ActivityDataSourceDaoBean implements ActivityDataSourceDao {

    @Override
    public ActivityDataSourceResult getResult(long recruitmentNumber, ActivityDataSource activityDataSource) throws DataNotFoundException {
        return null;
    }
}
