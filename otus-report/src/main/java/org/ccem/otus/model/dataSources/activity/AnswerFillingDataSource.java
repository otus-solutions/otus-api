package org.ccem.otus.model.dataSources.activity;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

import java.util.ArrayList;

public class AnswerFillingDataSource extends ReportDataSource<AnswerFillingDataSourceResult> {

  @Override
  public void addResult(AnswerFillingDataSourceResult result) {
    super.getResult().add(result);
  }

  @Override
  public ArrayList<Document> buildQuery(Long recruitmentNumber) {
    return null;
  }

  public ArrayList<AnswerFillingDataSourceResult> getResult() {
    return super.getResult();
  }

}