package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface LaboratoryConfigurationDao {

  LaboratoryConfiguration find();

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

  void persist(LaboratoryConfiguration laboratoryConfig);

  void update(LaboratoryConfiguration configuration) throws Exception;

  String createNewLotCodeForTransportation();

  String createNewLotCodeForExam();

  Integer updateLastTubeInsertion(int newTubesQuantities);
}
