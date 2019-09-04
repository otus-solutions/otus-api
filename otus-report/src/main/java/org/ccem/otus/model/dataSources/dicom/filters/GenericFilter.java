package org.ccem.otus.model.dataSources.dicom.filters;

import org.ccem.otus.model.dataSources.dicom.filters.enums.Quality;

public class GenericFilter {

  private Long recruitmentNumber;
  private String examName;
  private Quality quality;
  private Integer sending;

  public String getExamName() {
    return examName;
  }

  public Quality getQuality() {
    return quality;
  }

  public void setRecruitmentNumber(Long recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public Integer getSending() {
    return sending;
  }

}
