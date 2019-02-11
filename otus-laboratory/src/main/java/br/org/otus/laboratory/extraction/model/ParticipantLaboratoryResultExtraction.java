package br.org.otus.laboratory.extraction.model;

public class ParticipantLaboratoryResultExtraction {

  private Long recruitmentNumber;
  /* information of tube */
  private String tubeCode;
  private Integer tubeQualityControl;
  private Integer tubeType;
  private Integer tubeMoment;
  private String tubeCollectionDate;
  private String tubeResponsible;
  /* information of aliquot */
  private String aliquotCode;
  private String aliquotName;
  private String aliquotProcessingDate;
  private String aliquotRegisterDate;
  private String aliquotResponsible;

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getTubeCode() {
    return tubeCode;
  }

  public Integer getTubeQualityControl() {
    return tubeQualityControl;
  }

  public Integer getTubeType() {
    return tubeType;
  }

  public Integer getTubeMoment() {
    return tubeMoment;
  }

  public String getTubeCollectionDate() {
    return tubeCollectionDate;
  }

  public String getTubeResponsible() {
    return tubeResponsible;
  }

  public String getAliquotCode() {
    return aliquotCode;
  }

  public String getAliquotName() {
    return aliquotName;
  }

  public String getAliquotProcessingDate() {
    return aliquotProcessingDate;
  }

  public String getAliquotRegisterDate() {
    return aliquotRegisterDate;
  }

  public String getAliquotResponsible() {
    return aliquotResponsible;
  }

}
