package org.ccem.otus.model.dataSources;

import org.bson.Document;

import java.util.ArrayList;

public class ParticipantDataSource extends ReportDataSource<ParticipantDataSourceResult, ParticipantDataSource> {
    private ArrayList<ParticipantDataSourceResult> result = new ArrayList<>();

    public void addResult(ParticipantDataSourceResult result){
        this.result.add(result);
    }

    @Override
    public ArrayList<Document> builtQuery(Long recruitmentNumber, ParticipantDataSource dataSource) {
        return null;
    }
}
