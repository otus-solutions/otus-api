package br.org.otus.laboratory.configuration;

public interface LaboratoryConfigurationDao {

	LaboratoryConfiguration find();

	void persist(LaboratoryConfiguration laboratoryConfig);

	void update(LaboratoryConfiguration configuration) throws Exception;

	String createNewLotCodeForTransportation();

	String createNewLotCodeForExam();
}
