package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface TransportLocationPointService {
  void create(TransportLocationPoint transportationLocationPoint);

  void update(TransportLocationPoint transportLocationPoint) throws DataNotFoundException;

  void delete(String locationPointId) throws DataNotFoundException;

  void addUser(ObjectId userId, ObjectId locationPointId);
}
