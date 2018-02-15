package org.ccem.otus.model;

public abstract class ReportDataSource<T> {
    private String key;
    private String dataSource;


    public String getDataSource() {
        return dataSource;
    }

    public abstract void addResult(T result);

}
