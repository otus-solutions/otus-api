package br.org.otus.laboratory.participant.collect.tube.generator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import br.org.otus.laboratory.participant.collect.tube.TubeDefinition;
import br.org.otus.laboratory.participant.collect.tube.qualifier.DefaultGenerator;

@Stateless
@DefaultGenerator
public class DefaultTubeGenerator extends AbstractTubeGenerator implements TubeGenerator {

	private static String GROUP_NAME_DEFAULT = "DEFAULT";

	@Override
	public List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed) {
		Set<TubeDefinition> tubeSet = this.laboratoryConfigurationService.getDefaultTubeSet();
		List<TubeDefinition> tubeDefinitions = tubeSet.stream().map(definition -> definition).collect(Collectors.toList());
		tubeDefinitions.forEach(definition -> definition.setGroup(GROUP_NAME_DEFAULT));
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
