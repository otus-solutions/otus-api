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
    return null;
  }

  public String buildFilterToDICOM(Long recruitmentNumber) {
    filters.setRecruitmentNumber(recruitmentNumber);
    return filters.toString();
  }

  public GenericFilter getFilters() {
    return filters;
  }

}
