package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.DefaultGenerator;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@DefaultGenerator
public class DefaultTubeGenerator extends AbstractTubeGenerator implements TubeGenerator {

  private static String GROUP_NAME_DEFAULT = "DEFAULT";

  @Override
  public List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed) throws DataNotFoundException {
    Set<TubeDefinition> tubeSet = this.laboratoryConfigurationService.getDefaultTubeSet();
    List<TubeDefinition> tubeDefinitions = tubeSet.stream().collect(Collectors.toList());
    tubeDefinitions.forEach(definition -> definition.setGroup(GROUP_NAME_DEFAULT));
    tubeSeed.setTubeCount(this.sumTubeCounts(tubeDefinitions));
    return tubeDefinitions;
  }

}
