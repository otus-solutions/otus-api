package br.org.otus.laboratory.project.transportation.persistence;

import java.util.HashSet;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.project.transportation.TransportationLot;

public interface TransportationLotDao {

  ObjectId persist(TransportationLot transportationLot);

  TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException;

  List<TransportationLot> find();

  void delete(String id) throws DataNotFoundException;

  String checkIfThereInTransport(String aliquotCode);

  HashSet<Document> getAliquotsInfoInTransportationLots() throws DataNotFoundException;

  TransportationLot findByCode(String code) throws DataNotFoundException;

  TransportationLot find(ObjectId transportationLotId) throws DataNotFoundException;

  Integer getLastTransportationLotCode();
}
