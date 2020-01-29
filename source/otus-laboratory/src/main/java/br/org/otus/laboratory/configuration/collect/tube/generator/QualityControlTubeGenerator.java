package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.QualityControlGenerator;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@QualityControlGenerator
public class QualityControlTubeGenerator extends AbstractTubeGenerator implements TubeGenerator {

  @Override
  public List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed) {
    if (tubeSeed.getCollectGroupDescriptor().getTubes().isEmpty()) {
      return new ArrayList<>();
    } else {
      return getTubeDefinitionList(tubeSeed);
    }
  }

  private List<TubeDefinition> getTubeDefinitionList(TubeSeed tubeSeed) {
    Set<TubeDefinition> tubeSet = this.laboratoryConfigurationService.getTubeSetByGroupName(tubeSeed.getCollectGroupDescriptor().getName());
    List<TubeDefinition> tubeDefinitions = tubeSet.stream().map(definition -> definition).collect(Collectors.toList());
    tubeDefinitions.forEach(definition -> definition.setGroup(tubeSeed.getCollectGroupDescriptor().getName()));
    tubeSeed.setTubeCount(this.sumTubeCounts(tubeDefinitions));
    return tubeDefinitions;
  }

  private Integer sumTubeCounts(List<TubeDefinition> tubeDefinitions) {
    Integer tubeCount = 0;
    for (TubeDefinition tubeDefinition : tubeDefinitions) {
      tubeCount += tubeDefinition.getCount();
    }
    return tubeCount;
  }

}
