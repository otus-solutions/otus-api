package org.ccem.otus.model.dataSources.activity;

public class ActivityDataSourceFilters {
    private String acronym = null;
    private String category = null;
    private ActivityDataSourceStatusHistoryFilter statusHistory = null;


    public String getAcronym() {
        return acronym;
    }

    public String getCategory() {
        return category;
    }

    public ActivityDataSourceStatusHistoryFilter getStatusHistory() {
        return statusHistory;
    }
}
