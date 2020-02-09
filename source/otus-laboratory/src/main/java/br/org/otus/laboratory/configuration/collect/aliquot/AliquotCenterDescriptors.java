package br.org.otus.laboratory.configuration.collect.aliquot;

import java.util.ArrayList;
import java.util.List;

public class AliquotCenterDescriptors {

  private String objectType;
  private String name;
  private List<Integer> aliquotCodeSizes;
  private List<AliquotGroupDescriptors> aliquotGroupDescriptors;

  public AliquotCenterDescriptors(String objectType, String name, List<AliquotGroupDescriptors> aliquotGroupDescriptors, List<Integer> aliquotCodeSizes) {
    this.objectType = objectType;
    this.name = name;
    this.aliquotGroupDescriptors = aliquotGroupDescriptors;
    this.aliquotCodeSizes = aliquotCodeSizes;
  }

  public String getObjectType() {
    return objectType;
  }

  public String getName() {
    return name;
  }

  public List<AliquotGroupDescriptors> getAliquotGroupDescriptors() {
    return aliquotGroupDescriptors;
  }

  public List<Integer> getAliquotCodeSizes() {
    return aliquotCodeSizes;
  }

  public List<CenterAliquot> getAllCenterAliquots() {
    ArrayList<CenterAliquot> aliquoteDescriptors = new ArrayList<>();
    for (AliquotGroupDescriptors aliquotGroupDescriptor : aliquotGroupDescriptors) {
      for (AliquotMomentDescriptors aliquotMomentDescriptors : aliquotGroupDescriptor.getAliquotMomentDescriptors()) {
        for (AliquotTypesDescriptors aliquotTypesDescriptors : aliquotMomentDescriptors.getAliquotTypesDescriptors()) {
          aliquoteDescriptors.addAll(aliquotTypesDescriptors.getAliquots());
        }
      }
    }
    return aliquoteDescriptors;
  }

}
