package br.org.otus.laboratory.participant.collect.tube.generator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.participant.collect.tube.Tube;
import br.org.otus.laboratory.participant.collect.tube.TubeDefinition;

public abstract class AbstractTubeGenerator implements TubeGenerator {

	@Inject
	protected LaboratoryConfigurationService laboratoryConfigurationService;

	@Override
	public List<Tube> generateTubes(TubeSeed tubeSeed) {
		List<TubeDefinition> tubeTypeDefinitions = getTubeDefinitions(tubeSeed);
		List<String> codesToUse = laboratoryConfigurationService.generateCodes(tubeSeed);
		List<Tube> tubes = new ArrayList<>();

		for (TubeDefinition tubeDefinition : tubeTypeDefinitions) {
			Integer count = tubeDefinition.getCount();

			for (int i = 0; i < count; i++) {
				Tube newTube = createTube(codesToUse.get(0), tubeDefinition);
				tubes.add(newTube);
				codesToUse.remove(0);
			}
		}

		return tubes;
	}

	private Tube createTube(String code, TubeDefinition tubeDefinition) {
		return new Tube(tubeDefinition.getType(), tubeDefinition.getMoment(), code, tubeDefinition.getGroup());
	}

	public abstract List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed);

}
