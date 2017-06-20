package br.org.otus.laboratory;

import java.util.List;

import br.org.otus.laboratory.collect.moment.CollectMomentConfiguration;
import br.org.otus.laboratory.collect.tube.TubeConfiguration;

public class LaboratoryConfigurationDTO {
	
	public LaboratoryConfigurationDTO(LaboratoryConfiguration laboratoryConfiguration) {
		this.metadataConfiguration = laboratoryConfiguration.getMetadataConfiguration();
		this.tubeConfiguration = laboratoryConfiguration.getTubeConfiguration();
		this.collectMomentConfiguration = laboratoryConfiguration.getCollectMomentConfiguration();
	}
	public List<MetadataConfiguration> metadataConfiguration;
	public TubeConfiguration tubeConfiguration;
	public CollectMomentConfiguration collectMomentConfiguration;
}
