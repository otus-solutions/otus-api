package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;

public interface LocationPointCorrelationDao {
  void persist(TransportLocationPoint transportLocationPoint);
}
