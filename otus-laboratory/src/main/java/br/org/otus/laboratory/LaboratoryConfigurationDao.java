package br.org.otus.laboratory;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface LaboratoryConfigurationDao {
	
	LaboratoryConfiguration find();

	void persist(LaboratoryConfiguration laboratoryConfig);

	void update(LaboratoryConfiguration configuration) throws Exception;
	
	Document findTubeByAliquot(String aliquotCode) throws DataNotFoundException;

}
