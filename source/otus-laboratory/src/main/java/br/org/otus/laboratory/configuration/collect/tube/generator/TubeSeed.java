package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;

public class TubeSeed {

  private FieldCenter fieldCenter;
  private CollectGroupDescriptor collectGroupDescriptor;
  private int tubeCount;

  private TubeSeed(FieldCenter fieldCenter, CollectGroupDescriptor collGroupDescriptor, Integer tubeCount) {
    this.fieldCenter = fieldCenter;
    this.collectGroupDescriptor = collGroupDescriptor;
    this.tubeCount = tubeCount;
  }

  public Integer getFieldCenterCode() {
    return fieldCenter.getCode();
  }

  public String getFieldCenterAcronym() {
    return fieldCenter.getAcronym();
  }

  public CollectGroupDescriptor getCollectGroupDescriptor() {
    return collectGroupDescriptor;
  }

  public String getCollectGroupName() {
    return getCollectGroupDescriptor().getName();
  }

  public Integer getTubeCount() {
    return tubeCount;
  }

  public void setTubeCount(Integer count) {
    this.tubeCount = count;
  }

  public static TubeSeed generate(FieldCenter fieldCenter, CollectGroupDescriptor collectGroupDescriptor) {
    return new TubeSeed(fieldCenter, collectGroupDescriptor, 0);
  }

}
