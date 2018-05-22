package org.ccem.otus.model.monitoring;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

import java.util.ArrayList;

public class MonitoringDataSource extends ReportDataSource<MonitoringDataSourceResult> {
    @Override
    public void addResult(MonitoringDataSourceResult result) {

    }

    @Override
    public ArrayList<Document> buildQuery(Long recruitmentNumber) {
        return null;
    }
}
