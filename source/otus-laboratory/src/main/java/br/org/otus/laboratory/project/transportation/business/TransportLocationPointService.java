package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointListDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface TransportLocationPointService {
  void create(TransportLocationPoint transportationLocationPoint);

  void update(TransportLocationPoint transportLocationPoint) throws DataNotFoundException;

  void delete(String locationPointId) throws DataNotFoundException;

  void addUser(ObjectId userId, ObjectId locationPointId);

  void removeUser(ObjectId userId, ObjectId locationPointId);

  TransportLocationPointListDTO getLocationList();

  TransportLocationPointListDTO getUserLocationPoints(ObjectId userId) throws DataNotFoundException;

  TransportLocationPointListDTO getLocationPoints() throws DataNotFoundException;
}
