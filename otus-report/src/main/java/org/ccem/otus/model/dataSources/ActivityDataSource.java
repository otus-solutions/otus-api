package org.ccem.otus.model.dataSources;

import java.util.ArrayList;

public class ActivityDataSource extends ReportDataSource<ActivityDataSourceResult> {

    private ArrayList<ActivityDataSourceResult> result = new ArrayList<>();

    @Override
    public void addResult(ActivityDataSourceResult result) {
        this.result.add(result);
    }
}
