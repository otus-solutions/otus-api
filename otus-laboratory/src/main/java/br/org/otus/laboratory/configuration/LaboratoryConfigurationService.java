package br.org.otus.laboratory.configuration;

import java.util.List;
import java.util.Set;

import br.org.otus.laboratory.configuration.label.LabelReference;
import br.org.otus.laboratory.participant.collect.tube.TubeDefinition;
import br.org.otus.laboratory.participant.collect.tube.generator.TubeSeed;

public interface LaboratoryConfigurationService {

	Set<TubeDefinition> getDefaultTubeSet();

	Set<TubeDefinition> getTubeSetByGroupName(String setName);

	List<LabelReference> getLabelOrderByName(String orderName);

	List<String> generateCodes(TubeSeed seed);
	
	LaboratoryConfiguration getLaboratoryConfiguration();
	
}
