package br.org.otus.user.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.ArrayList;

public interface UserActivityPendencyDao {

  void create(UserActivityPendency userActivityPendency);

  void update(Object data) throws ValidationException, DataNotFoundException;

  ArrayList<UserActivityPendency> find();

  void delete(String name) throws DataNotFoundException;

  boolean exists(String userActivityPendencyId);

}
