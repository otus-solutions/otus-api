package br.org.otus.laboratory.extraction.model;

public class LaboratoryResultExtraction {

  private Long recruitmentNumber;
  private Integer unattachedLaboratoryId;
  /* information of tube */
  private String tubeCode;
  private Integer tubeQualityControl;
  private String tubeType;
  private String tubeMoment;
  private String tubeCollectionDate;
  private String tubeResponsible;
  /* information of aliquot */
  private String aliquotCode;
  private String aliquotName;
  private String aliquotContainer;
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

  public String getTubeType() {
    return tubeType;
  }

  public String getTubeMoment() {
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

  public String getAliquotContainer() {
    return aliquotContainer;
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

  public Integer getUnattachedLaboratoryId() {
    return unattachedLaboratoryId;
  }
}
