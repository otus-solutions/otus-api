package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.configuration.label.LabelReference;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Stateless
public class LaboratoryConfigurationServiceBean implements LaboratoryConfigurationService {

	@Inject
	private LaboratoryConfigurationDao laboratoryConfigurationDao;

	private LaboratoryConfiguration laboratoryConfiguration;

	@PostConstruct
	public void loadLaboratoryConfiguration() {
		this.laboratoryConfiguration = this.laboratoryConfigurationDao.find();
	}

	@Override
	public Set<TubeDefinition> getDefaultTubeSet() {
		return this.laboratoryConfiguration.getCollectGroupConfiguration().getDefaultCollectGroupDescriptor().getTubes();
	}

	@Override
	public Set<TubeDefinition> getTubeSetByGroupName(String groupName) {
		try {
			return this.laboratoryConfiguration.getCollectGroupConfiguration().getCollectGroupByName(groupName).getTubes();
		} catch (NoSuchElementException e) {
			return new HashSet<>();
		}
	}

	@Override
	public List<LabelReference> getLabelOrderByName(String orderName) {
		return laboratoryConfiguration.getLabelPrintConfiguration().getOrders().get(orderName);
	}

	@Override
	public List<String> generateCodes(TubeSeed seed) {
		Integer startingPoint = this.laboratoryConfiguration.allocNextCodeList(seed);
		updateLaboratoryConfiguration();
		return this.laboratoryConfiguration.generateNewCodeList(seed, startingPoint);
	}

	private void updateLaboratoryConfiguration() {
		try {
			this.laboratoryConfigurationDao.update(this.laboratoryConfiguration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LaboratoryConfiguration getLaboratoryConfiguration() {
		return laboratoryConfiguration;
	}

	@Override
	public AliquotConfiguration getAliquotConfiguration() {
		return laboratoryConfiguration.getAliquotConfiguration();
	}

	@Override
	public List<AliquoteDescriptor> getAliquotDescriptors() {
		return laboratoryConfiguration.getAliquotConfiguration().getAliquotDescriptors();
	}

}
