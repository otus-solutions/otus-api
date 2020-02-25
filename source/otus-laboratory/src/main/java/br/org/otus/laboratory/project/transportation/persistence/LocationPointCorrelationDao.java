package br.org.otus.laboratory.project.transportation.persistence;

import org.bson.types.ObjectId;

public interface LocationPointCorrelationDao {
  void create(ObjectId id);

  void addUser(ObjectId userId, ObjectId locationPointId);

  void removeUser(ObjectId userId, ObjectId locationPointId);

  TransportLocationPointListDTO getLocationPointList();
}
