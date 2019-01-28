package br.org.otus.examUploader.business.extraction.model;

import java.util.List;

import br.org.otus.examUploader.Observation;

public class ParticipantExamUploadResultExtraction {

  private String aliquotCode;
  private String resultName;
  private String value;
  private String releaseDate;
  private List<Observation> observations;

  public String getResultName() {
    return resultName;
  }

  public String getValue() {
    return value;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getAliquotCode() {
    return aliquotCode;
  }

  public List<Observation> getObservations() {
    return observations;
  }

}
