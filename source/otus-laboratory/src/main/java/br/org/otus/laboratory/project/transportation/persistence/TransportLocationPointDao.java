package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface TransportLocationPointDao {

  void persist(TransportLocationPoint transportLocationPoint);

  void update(TransportLocationPoint transportLocationPoint) throws DataNotFoundException;

  void delete(String locationPointId) throws DataNotFoundException;
}
