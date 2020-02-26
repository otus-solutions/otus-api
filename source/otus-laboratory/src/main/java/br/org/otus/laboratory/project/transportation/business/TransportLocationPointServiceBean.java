package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.laboratory.project.transportation.persistence.LocationPointCorrelationDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointListDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TransportLocationPointServiceBean implements TransportLocationPointService {

  @Inject
  private TransportLocationPointDao transportLocationPointDao;

  @Inject
  private LocationPointCorrelationDao locationPointCorrelationDao;

  @Override
  public void create(TransportLocationPoint transportLocationPoint) {
    transportLocationPointDao.persist(transportLocationPoint);
    locationPointCorrelationDao.create(transportLocationPoint.get_id());
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
    locationPointCorrelationDao.addUser(userId,locationPointId);
  }

  @Override
  public void removeUser(ObjectId userId, ObjectId locationPointId) {
    locationPointCorrelationDao.removeUser(userId,locationPointId);
  }

  @Override
  public TransportLocationPointListDTO getLocationList() {
    return locationPointCorrelationDao.getLocationPointList();
  }

  @Override
  public TransportLocationPointListDTO getUserLocationPoints(ObjectId userId) throws DataNotFoundException {
    return locationPointCorrelationDao.getUserLocationPoints(userId);
  }

  @Override
  public TransportLocationPointListDTO getLocationPoints() throws DataNotFoundException {
    return locationPointCorrelationDao.getLocationPoints();
  }

}
