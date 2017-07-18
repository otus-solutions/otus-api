package br.org.otus.laboratory;

import java.util.List;

import br.org.otus.laboratory.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.collect.moment.CollectMomentConfiguration;
import br.org.otus.laboratory.collect.tube.TubeConfiguration;

public class LaboratoryConfigurationDTO {
	
	public AliquotConfiguration aliquotConfiguration;
	public List<MetadataConfiguration> metadataConfiguration;
	public TubeConfiguration tubeConfiguration;
	public CollectMomentConfiguration collectMomentConfiguration;
	
	public LaboratoryConfigurationDTO(LaboratoryConfiguration laboratoryConfiguration) {
		this.aliquotConfiguration = laboratoryConfiguration.getAliquotConfiguration();
		this.metadataConfiguration = laboratoryConfiguration.getMetadataConfiguration();
		this.tubeConfiguration = laboratoryConfiguration.getTubeConfiguration();
		this.collectMomentConfiguration = laboratoryConfiguration.getCollectMomentConfiguration();
	}

}
