package br.org.otus.laboratory.configuration.collect.aliquot;

import java.util.List;

public class AliquotConfiguration {

  private String objectType;
  private List<AliquotCenterDescriptors> aliquotCenterDescriptors;
  private List<AliquoteDescriptor> aliquotDescriptors;

  public AliquotConfiguration(List<AliquotCenterDescriptors> aliquotCenterDescriptors) {
    this.aliquotCenterDescriptors = aliquotCenterDescriptors;
    this.aliquotDescriptors = aliquotDescriptors;
  }

  public List<AliquotCenterDescriptors> getAliquotCenterDescriptors() {
    return aliquotCenterDescriptors;
  }

  public List<AliquoteDescriptor> getAliquotDescriptors() {
    return aliquotDescriptors;
  }

  public String getObjectType() {
    return objectType;
  }

}
