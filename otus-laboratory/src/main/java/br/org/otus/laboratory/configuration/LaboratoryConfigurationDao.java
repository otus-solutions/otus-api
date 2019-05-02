package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface LaboratoryConfigurationDao {

  LaboratoryConfiguration find();

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

  void persist(LaboratoryConfiguration laboratoryConfig);

  String createNewLotCodeForTransportation(Integer code);

  String createNewLotCodeForExam(Integer code);

  Integer getLastInsertion(String lot);

  void restoreLotConfiguration(String config, Integer code);

  Integer updateLastTubeInsertion(int newTubesQuantities);

  List<String> getExamName(List<String> centerAliquots);

  AggregateIterable<Document> aggregate(List<Bson> query);

}
