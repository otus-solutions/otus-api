package br.org.otus.laboratory.project.transportation.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface LocationPointCorrelationDao {
  void create(ObjectId id);

  void addUser(ObjectId userId, ObjectId locationPointId);

  void removeUser(ObjectId userId, ObjectId locationPointId);

  TransportLocationPointListDTO getLocationPointList();

  TransportLocationPointListDTO getUserLocationPoints(ObjectId userId) throws DataNotFoundException;

  TransportLocationPointListDTO getLocationPoints() throws DataNotFoundException;
}
