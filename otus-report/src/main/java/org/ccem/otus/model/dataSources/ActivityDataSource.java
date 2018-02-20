package org.ccem.otus.model.dataSources;

import java.util.ArrayList;

public class ActivityDataSource extends ReportDataSource<ActivityDataSourceResult> {

    private String acronym = null;
    private Integer position = null;
    private String category = null;
    private String statusHistory = null;
    private ArrayList<ActivityDataSourceResult> result = new ArrayList<>();

    @Override
    public void addResult(ActivityDataSourceResult result) {
        this.result.add(result);
    }

    public String getAcronym() {
        return acronym;
    }

    public Integer getPosition() {
        return position;
    }

    public String getCategory() {
        return category;
    }

    public String getStatusHistory() {
        return statusHistory;
    }
}
