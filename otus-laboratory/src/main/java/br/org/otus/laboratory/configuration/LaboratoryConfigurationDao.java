package br.org.otus.laboratory.configuration;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.configuration.exam.ExamsDescriptors;

public interface LaboratoryConfigurationDao {

  LaboratoryConfiguration find();

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

  void persist(LaboratoryConfiguration laboratoryConfig);

  void update(LaboratoryConfiguration configuration) throws Exception;

  String createNewLotCodeForTransportation();

  String createNewLotCodeForExam();

  ExamsDescriptors getDescriptionOfExamResults() throws DataNotFoundException;
}
