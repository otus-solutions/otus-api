package br.org.otus.laboratory;

import java.util.List;

import br.org.otus.laboratory.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.collect.group.CollectGroupConfiguration;
import br.org.otus.laboratory.collect.moment.CollectMomentConfiguration;
import br.org.otus.laboratory.collect.tube.TubeConfiguration;
import br.org.otus.laboratory.label.LabelPrintConfiguration;

public class LaboratoryConfigurationDTO {

	private CodeConfiguration codeConfiguration;
	private TubeConfiguration tubeConfiguration;
	private AliquotConfiguration aliquotConfiguration;
	private CollectMomentConfiguration collectMomentConfiguration;
	private CollectGroupConfiguration collectGroupConfiguration;
	private LabelPrintConfiguration labelPrintConfiguration;
	private List<MetadataConfiguration> metadataConfiguration;

	public LaboratoryConfigurationDTO(LaboratoryConfiguration laboratoryConfiguration) {
		this.codeConfiguration = laboratoryConfiguration.getCodeConfiguration();
		this.tubeConfiguration = laboratoryConfiguration.getTubeConfiguration();
		this.aliquotConfiguration = laboratoryConfiguration.getAliquotConfiguration();
		this.collectMomentConfiguration = laboratoryConfiguration.getCollectMomentConfiguration();
		this.collectGroupConfiguration = laboratoryConfiguration.getCollectGroupConfiguration();
		this.labelPrintConfiguration = laboratoryConfiguration.getLabelPrintConfiguration();
		this.metadataConfiguration = laboratoryConfiguration.getMetadataConfiguration();
	}

}
