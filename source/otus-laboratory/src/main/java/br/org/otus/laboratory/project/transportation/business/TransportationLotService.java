package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface TransportationLotService {

  TransportationLot create(TransportationLot transportationLot, String userEmail, ObjectId userId) throws ValidationException, DataNotFoundException;

  TransportationLot update(TransportationLot transportationLot, ObjectId userId) throws DataNotFoundException, ValidationException;

  List<TransportationLot> list(String locationPointId);

  TransportationLot getByCode(String code) throws DataNotFoundException;

  void delete(String id) throws DataNotFoundException;
}
