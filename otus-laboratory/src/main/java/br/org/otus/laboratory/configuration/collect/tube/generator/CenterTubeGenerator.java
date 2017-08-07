package br.org.otus.laboratory.configuration.collect.tube.generator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.CenterGenerator;

@Stateless
@CenterGenerator
public class CenterTubeGenerator extends AbstractTubeGenerator implements TubeGenerator {

	@Override
	public List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed) {
		Set<TubeDefinition> tubeSet = this.laboratoryConfigurationService.getTubeSetByGroupName(tubeSeed.getFieldCenterAcronym());
		List<TubeDefinition> tubeDefinitions = tubeSet.stream().map(definition -> definition).collect(Collectors.toList());
		tubeDefinitions.forEach(definition -> definition.setGroup(tubeSeed.getFieldCenterAcronym()));
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
