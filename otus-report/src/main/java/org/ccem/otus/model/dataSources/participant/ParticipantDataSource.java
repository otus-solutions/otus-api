package org.ccem.otus.model.dataSources.participant;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

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
