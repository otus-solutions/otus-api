package org.ccem.otus.model.dataSources;

import org.bson.Document;

import java.util.ArrayList;

public abstract class ReportDataSource<T> {
    private String key;
    private String dataSource;

    public abstract void addResult(T result);

    public abstract ArrayList<Document> builtQuery(Long recruitmentNumber);

}
