package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.dataSources.ActivityDataSource;
import org.ccem.otus.model.dataSources.ActivityDataSourceResult;

public interface ActivityDataSourceDao {
    ActivityDataSourceResult getResult(long recruitmentNumber, ActivityDataSource activityDataSource) throws DataNotFoundException;
}
