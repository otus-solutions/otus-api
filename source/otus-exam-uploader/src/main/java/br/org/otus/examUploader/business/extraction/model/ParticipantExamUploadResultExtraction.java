package br.org.otus.examUploader.business.extraction.model;

import java.util.List;

import br.org.otus.examUploader.Observation;

public class ParticipantExamUploadResultExtraction {

  private Long recruitmentNumber;
  private String code;
  private String resultName;
  private String value;
  private String releaseDate;
  private List<Observation> observations;

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getResultName() {
    return resultName;
  }

  public String getValue() {
    return value;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getCode() {
    return code;
  }

  public List<Observation> getObservations() {
    return observations;
  }

}
