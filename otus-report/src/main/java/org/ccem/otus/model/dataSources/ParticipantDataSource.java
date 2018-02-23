package org.ccem.otus.model.dataSources;

import org.bson.Document;

import java.util.ArrayList;

public class ParticipantDataSource extends ReportDataSource<ParticipantDataSourceResult> {
    private ArrayList<ParticipantDataSourceResult> result = new ArrayList<>();

    @Override
    public ArrayList<Document> builtQuery(Long recruitmentNumber) {
        return null;
    }

    public void addResult(ParticipantDataSourceResult result){
        this.result.add(result);
    }

    public ArrayList<ParticipantDataSourceResult> getResult() {
        return result;
    }
}
