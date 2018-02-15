package org.ccem.otus.model;

import java.util.ArrayList;

public class ParticipantDataSource extends ReportDataSource<ParticipantDataSourceResult> {
    private ArrayList<ParticipantDataSourceResult> result = new ArrayList<>();

    public void addResult(ParticipantDataSourceResult result){
        this.result.add(result);
    }
}
