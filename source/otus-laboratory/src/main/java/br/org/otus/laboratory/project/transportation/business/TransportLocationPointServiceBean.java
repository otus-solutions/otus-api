package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TransportLocationPointServiceBean implements TransportLocationPointService {

  @Inject
  private TransportLocationPointDao transportLocationPointDao;

  @Override
  public void create(TransportLocationPoint transportLocationPoint) {
    transportLocationPointDao.persist(transportLocationPoint);
  }

  @Override
  public void update(TransportLocationPoint transportLocationPoint) throws DataNotFoundException {
    transportLocationPointDao.update(transportLocationPoint);
  }

  @Override
  public void delete(String locationPointId) throws DataNotFoundException {
    transportLocationPointDao.delete(locationPointId);
  }

  @Override
  public void addUser(ObjectId userId, ObjectId locationPointId) {

  }

}
