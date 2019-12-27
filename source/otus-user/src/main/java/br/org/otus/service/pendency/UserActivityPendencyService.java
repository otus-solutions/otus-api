package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface UserActivityPendencyService {

  enum State {
    ANY, CREATED, EXISTENT
  }

  void create(UserActivityPendency userActivityPendency, String userEmail) throws ValidationException, DataNotFoundException;

  void update(UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException;

  UserActivityPendency getByActivityId(Long activityId) throws DataNotFoundException;

  List<UserActivityPendency> list() throws DataNotFoundException, MemoryExcededException;

  void delete(UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException;
}