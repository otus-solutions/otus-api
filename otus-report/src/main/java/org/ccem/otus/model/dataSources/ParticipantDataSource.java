package org.ccem.otus.model.dataSources;

import org.bson.Document;

import java.util.ArrayList;

public class ParticipantDataSource extends ReportDataSource<ParticipantDataSourceResult> {

    @Override
    public ArrayList<Document> builtQuery(Long recruitmentNumber) {
        return null;
    }

    public void addResult(ParticipantDataSourceResult result){
        super.getResult().add(result);
    }

    public ArrayList<ParticipantDataSourceResult> getResult() {
        return super.getResult();
    }
}
