package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.configuration.label.LabelReference;

import java.util.List;
import java.util.Set;

public interface LaboratoryConfigurationService {

	Set<TubeDefinition> getDefaultTubeSet();

	Set<TubeDefinition> getTubeSetByGroupName(String setName);

	List<LabelReference> getLabelOrderByName(String orderName);

	List<String> generateCodes(TubeSeed seed);

	LaboratoryConfiguration getLaboratoryConfiguration();

	AliquotConfiguration getAliquotConfiguration();

	List<AliquoteDescriptor> getAliquotDescriptors();

    List<AliquoteDescriptor> getAliquotDescriptorsByCenter(String center);
}
