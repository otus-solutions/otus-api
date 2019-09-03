package org.ccem.otus.model.dataSources.dicom;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.dicom.filters.GenericFilter;

public class DICOMDataSource extends ReportDataSource<DICOMDataSourceResult> {

  private GenericFilter filters;

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

  public GenericFilter getFilters() {
    return filters;
  }

}
