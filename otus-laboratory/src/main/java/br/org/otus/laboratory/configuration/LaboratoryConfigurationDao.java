package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public interface LaboratoryConfigurationDao {

  LaboratoryConfiguration find();

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

  List<String> getAliquotsExams(List<String> aliquots);

  void persist(LaboratoryConfiguration laboratoryConfig);

  String createNewLotCodeForTransportation(Integer code);

  String createNewLotCodeForExam(Integer code);

  Integer getLastInsertion(String lot);

  void restoreLotConfiguration(String config, Integer code);

  Integer updateLastTubeInsertion(int newTubesQuantities);

  ArrayList listCenterAliquots(String center) throws DataNotFoundException;
}
