package br.org.otus.laboratory.participant.dto;

import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;

import java.util.List;

public class UpdateTubeAliquotsDTO {

  private String code;
  private List<SimpleAliquot> aliquots;

  public String getTubeCode() {
    return code;
  }

  public List<SimpleAliquot> getAliquots() {
    return aliquots;
  }

}
