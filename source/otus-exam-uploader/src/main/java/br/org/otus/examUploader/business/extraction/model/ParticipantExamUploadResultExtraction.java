package br.org.otus.examUploader.business.extraction.model;

import java.util.List;

import br.org.otus.examUploader.ExtraVariable;
import br.org.otus.examUploader.Observation;

public class ParticipantExamUploadResultExtraction {

  private Long recruitmentNumber;
  private String code;
  private String examName;
  private String resultName;
  private String value;
  private String releaseDate;
  private String realizationDate;
  private List<Observation> observations;
  private String cutOffValue;
  private List<ExtraVariable> extraVariables;

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

  public String getRealizationDate() {
    return realizationDate;
  }

  public String getCode() {
    return code;
  }

  public List<Observation> getObservations() {
    return observations;
  }

  public String getExamName() {
    return examName;
  }

  public String getCutOffValue() {
    return cutOffValue;
  }

  public List<ExtraVariable> getExtraVariables() { return extraVariables; }
}
