package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface UserActivityPendencyService {

  enum State {
    ANY, CREATED, EXISTENT
  }

  void create(UserActivityPendency userActivityPendency, String userEmail) throws ValidationException, DataNotFoundException;

  void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException;

  void delete(ObjectId userActivityPendencyOID) throws ValidationException, DataNotFoundException;

  UserActivityPendency getByActivityId(String activityId) throws DataNotFoundException;

  List<UserActivityPendency> list() throws DataNotFoundException, MemoryExcededException;

}