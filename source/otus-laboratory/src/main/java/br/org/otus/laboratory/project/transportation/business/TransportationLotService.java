package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.ReceivedMaterial;
import br.org.otus.laboratory.project.transportation.TrailHistoryRecord;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface TransportationLotService {

  TransportationLot create(TransportationLot transportationLot, String userEmail, ObjectId userId) throws ValidationException, DataNotFoundException;

  TransportationLot update(TransportationLot transportationLot, ObjectId userId) throws DataNotFoundException, ValidationException;

  List<TransportationLot> list(String originLocationPointId, String destinationLocationPointId);
  
  TransportationLot getByCode(String code) throws DataNotFoundException;

  void delete(String id) throws DataNotFoundException;

  void areMaterialsInLotReceived(String code) throws ValidationException, DataNotFoundException;

  void receiveMaterial(ReceivedMaterial receivedMaterial, String transportationLotId) throws ValidationException;
  
  List<TrailHistoryRecord> getMaterialTrackingList(String materialCode) throws DataNotFoundException;
}
