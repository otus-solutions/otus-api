package org.ccem.otus.model.dataSources.dcm.ultrasound;

public class UltrasoundDataSourceFilter {

  private String recruitmentNumber;
  private String examName;
  private Integer sending;

  public String getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public void setRecruitmentNumber(String recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public String getExamName() {
    return examName;
  }

  public Integer getSending() {
    return sending;
  }

}
