package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.CenterGenerator;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@CenterGenerator
public class CenterTubeGenerator extends AbstractTubeGenerator implements TubeGenerator {

  @Override
  public List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed) throws DataNotFoundException {
    Set<TubeDefinition> tubeSet = this.laboratoryConfigurationService.getTubeSetByGroupName(tubeSeed.getFieldCenterAcronym());
    List<TubeDefinition> tubeDefinitions = tubeSet.stream().collect(Collectors.toList());
    tubeDefinitions.forEach(definition -> definition.setGroup(tubeSeed.getFieldCenterAcronym()));
    tubeSeed.setTubeCount(this.sumTubeCounts(tubeDefinitions));
    return tubeDefinitions;
  }

}
