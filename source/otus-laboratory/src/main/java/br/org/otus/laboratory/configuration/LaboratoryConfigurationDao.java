package br.org.otus.laboratory.configuration;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.LotReceiptCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.AggregateIterable;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;

public interface LaboratoryConfigurationDao {

  LaboratoryConfiguration find() throws DataNotFoundException;

  Boolean getCheckingExist();

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

  List<String> getAliquotsExams(List<String> aliquots);

  void persist(LaboratoryConfiguration laboratoryConfig);

  String createNewLotCodeForTransportation(Integer code);

  String createNewLotCodeForExam(Integer code);

  Integer getLastInsertion(String lot);

  void restoreLotConfiguration(String config, Integer code);

  Integer updateLastTubeInsertion(int newTubesQuantities);

  Integer updateUnattachedLaboratoryLastInsertion();

  ArrayList listCenterAliquots(String center) throws DataNotFoundException;

  List<String> getExamName(List<String> centerAliquots);

  AggregateIterable<Document> aggregate(List<Bson> query);

  List<TubeCustomMetadata> getTubeCustomMedataData(String type) throws DataNotFoundException;

  List<TubeCustomMetadata> getTubeCustomMedataData() ;
	
  List<LotReceiptCustomMetadata> getLotReceiptCustomMetadata() throws DataNotFoundException;

  List<MaterialReceiptCustomMetadata> getMaterialReceiptCustomMetadata(String materialType) throws DataNotFoundException;

  List<MaterialReceiptCustomMetadata> getMaterialReceiptCustomMetadata();
}
