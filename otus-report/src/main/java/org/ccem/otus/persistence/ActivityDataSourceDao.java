package org.ccem.otus.persistence;

import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;

public interface ActivityDataSourceDao {

    ActivityDataSourceResult getResult(Long recruitmentNumber, ActivityDataSource activityDataSource);

}
