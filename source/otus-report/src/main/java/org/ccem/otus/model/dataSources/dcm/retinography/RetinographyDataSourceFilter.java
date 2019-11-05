package org.ccem.otus.model.dataSources.dcm.retinography;

public class RetinographyDataSourceFilter {

  private String recruitmentNumber;
  private String examName;
  private Integer sending;
  private String eyeSelected;

  public String getExamName() {
    return examName;
  }

  public void setRecruitmentNumber(String recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public String getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public Integer getSending() {
    return sending;
  }

  public String getEyeSelected() {
    return eyeSelected;
  }

}
