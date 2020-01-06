package br.org.otus.persistence.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.ArrayList;

public interface UserActivityPendencyDao {
  //here

  ObjectId create(UserActivityPendency userActivityPendency);

  void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws DataNotFoundException;

  void delete(ObjectId oid) throws DataNotFoundException;

  UserActivityPendency findByActivityInfo(ObjectId activityOID) throws DataNotFoundException;

  ArrayList<UserActivityPendency> findAllPendencies() throws DataNotFoundException, MemoryExcededException;

  ArrayList<UserActivityPendency> findOpenedPendencies() throws DataNotFoundException, MemoryExcededException;

  ArrayList<UserActivityPendency> findDonePendencies() throws DataNotFoundException, MemoryExcededException;

}
