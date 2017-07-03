package br.org.otus.laboratory;

public interface LaboratoryConfigurationDao {
	
	LaboratoryConfiguration find();

	void persist(LaboratoryConfiguration laboratoryConfig);

	void update(LaboratoryConfiguration configuration) throws Exception;
	
}
