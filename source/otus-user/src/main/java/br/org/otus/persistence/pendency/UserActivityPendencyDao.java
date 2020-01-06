package br.org.otus.persistence.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.ArrayList;

public interface UserActivityPendencyDao {

  ObjectId create(UserActivityPendency userActivityPendency);

  void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws DataNotFoundException;

  void delete(ObjectId oid) throws DataNotFoundException;

  UserActivityPendency findByActivityOID(ObjectId activityOID) throws DataNotFoundException;

  ArrayList<UserActivityPendency> findAllPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException;

  ArrayList<UserActivityPendency> findOpenedPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException;

  ArrayList<UserActivityPendency> findDonePendencies(String userEmail) throws DataNotFoundException, MemoryExcededException;

}
