package org.ccem.otus.model.dataSources;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class ReportDataSource<T,I> {
    private String key;
    private String dataSource;


    public String getDataSource() {
        return dataSource;
    }

    public abstract void addResult(T result);

    public abstract ArrayList<Document> builtQuery(Long recruitmentNumber,I dataSource);

}
