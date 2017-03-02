package br.org.otus.laboratory.persistence;

import br.org.otus.laboratory.config.LaboratoryConfiguration;
import br.org.otus.laboratory.participant.LaboratoryParticipant;

public interface LaboratoryConfigurationDao {

	void persist(LaboratoryConfiguration laboratoryConfig);

	LaboratoryConfiguration find();

	LaboratoryParticipant update(LaboratoryConfiguration configuration);
	
}
