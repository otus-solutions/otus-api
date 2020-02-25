package br.org.otus.laboratory.project.transportation.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface LocationPointCorrelationDao {
  void create(ObjectId id);

  void addUser(ObjectId userId, ObjectId locationPointId);

  void removeUser(ObjectId userId, ObjectId locationPointId);

  TransportLocationPointListDTO getLocationPointList();

  ArrayList<String> getUserLocationPointsList(ObjectId userId) throws DataNotFoundException;

  ArrayList<String> getLocationPointsList() throws DataNotFoundException;
}
