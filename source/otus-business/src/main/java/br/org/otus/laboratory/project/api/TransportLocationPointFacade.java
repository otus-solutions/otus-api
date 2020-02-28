package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.laboratory.project.transportation.business.TransportLocationPointService;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointListDTO;
import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;

public class TransportLocationPointFacade {

  @Inject
  private TransportLocationPointService transportLocationPointService;
  @Inject
  private UserDao userDao;

  public void createLocationPoint(TransportLocationPoint transportLocationPoint) {
    transportLocationPointService.create(transportLocationPoint);
  }

  public void updateLocationPoint(TransportLocationPoint transportLocationPoint) {
    try {
      transportLocationPointService.update(transportLocationPoint);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public void deleteLocationPoint(String locationPointId) {
    try {
      transportLocationPointService.delete(locationPointId);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public void addUser(String locationPointId, User user) {
    try {
      User fullUser = userDao.fetchByEmail(user.getEmail());
      transportLocationPointService.addUser(fullUser.get_id(), new ObjectId(locationPointId));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public void removeUser(String locationPointId, User user) {
    try {
      User fullUser = userDao.fetchByEmail(user.getEmail());
      transportLocationPointService.removeUser(fullUser.get_id(), new ObjectId(locationPointId));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public TransportLocationPointListDTO getLocationList() {
    return transportLocationPointService.getLocationList();
  }

  public TransportLocationPointListDTO getUserLocationPoints(String userEmail) {
    try {
      User fullUser = userDao.fetchByEmail(userEmail);
      return transportLocationPointService.getUserLocationPoints(fullUser.get_id());
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public TransportLocationPointListDTO getLocationPoints() {
    try {
      return transportLocationPointService.getLocationPoints();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }
}

