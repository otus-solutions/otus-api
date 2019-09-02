package org.ccem.otus.model.dataSources.exam.image;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

public class DICOMDataSource extends ReportDataSource<DICOMDataSourceResult> {

  private DICOMDataSourceFilters filters;

  @Override
  public void addResult(DICOMDataSourceResult result) {
    super.getResult().add(result);
  }

  @Override
  public ArrayList<Document> buildQuery(Long recruitmentNumber) {
    // TODO Auto-generated method stub
    return null;
  }

  public String buildFilterToDICOM() {
    // TODO: converter modelo de filtro entregue do front para o modelo DICOM
    return "";
  }

}
