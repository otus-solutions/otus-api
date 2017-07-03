package br.org.otus.laboratory;

import java.util.List;
import java.util.Set;

import br.org.otus.laboratory.collect.tube.TubeDefinition;
import br.org.otus.laboratory.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.label.LabelReference;

public interface LaboratoryConfigurationService {

	Set<TubeDefinition> getDefaultTubeSet();

	Set<TubeDefinition> getTubeSetByGroupName(String setName);

	List<LabelReference> getLabelOrderByName(String orderName);

	List<String> generateCodes(TubeSeed seed);
	
	LaboratoryConfiguration getLaboratoryConfiguration();
	
	boolean isAliquoted(String aliquotCode);

}
