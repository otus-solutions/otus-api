package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;

public interface LaboratoryConfigurationDao {

	LaboratoryConfiguration find();

	AliquotExamCorrelation getAliquotExamCorrelation();

	void persist(LaboratoryConfiguration laboratoryConfig);

	void update(LaboratoryConfiguration configuration) throws Exception;

	String createNewLotCodeForTransportation();

	String createNewLotCodeForExam();
}
