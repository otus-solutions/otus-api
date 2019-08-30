package org.ccem.otus.model.dataSources.exam.image;

import java.time.LocalDateTime;

public class ExamImageDataSourceFilters {

  private String examName;
  private LocalDateTime date;
  private Integer relative;
  private String fieldCenter;

  public String getExamName() {
    return this.examName;
  }

  public String getFieldCenter() {
    return fieldCenter;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Integer getRelative() {
    return relative;
  }

}
