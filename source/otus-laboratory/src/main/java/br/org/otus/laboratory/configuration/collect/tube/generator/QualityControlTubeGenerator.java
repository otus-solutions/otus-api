package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.QualityControlGenerator;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@QualityControlGenerator
public class QualityControlTubeGenerator extends AbstractTubeGenerator implements TubeGenerator {

  @Override
  public List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed) throws DataNotFoundException {
    if (tubeSeed.getCollectGroupDescriptor().getTubes().isEmpty()) {
      return new ArrayList<>();
    }

    return getTubeDefinitionList(tubeSeed);
  }

  private List<TubeDefinition> getTubeDefinitionList(TubeSeed tubeSeed) throws DataNotFoundException {
    Set<TubeDefinition> tubeSet = this.laboratoryConfigurationService.getTubeSetByGroupName(tubeSeed.getCollectGroupDescriptor().getName());
    List<TubeDefinition> tubeDefinitions = tubeSet.stream().collect(Collectors.toList());
    tubeDefinitions.forEach(definition -> definition.setGroup(tubeSeed.getCollectGroupDescriptor().getName()));
    tubeSeed.setTubeCount(this.sumTubeCounts(tubeDefinitions));
    return tubeDefinitions;
  }

}
