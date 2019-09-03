package org.ccem.otus.model.dataSources.dicom.filters;

import org.ccem.otus.model.dataSources.dicom.filters.enums.Quality;

public class GenericFilter {

  private String examName;
  private Quality quality;

  public String getExamName() {
    return examName;
  }

  public Quality getQuality() {
    return quality;
  }

}
