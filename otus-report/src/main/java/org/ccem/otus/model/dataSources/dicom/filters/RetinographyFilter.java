package org.ccem.otus.model.dataSources.dicom.filters;

public class RetinographyFilter extends GenericFilter {

  private String eyeSelected;
  private Integer positionOfDate;

  public String getEyeSelected() {
    return eyeSelected;
  }

  public Integer getPositionOfDate() {
    return positionOfDate;
  }

}
