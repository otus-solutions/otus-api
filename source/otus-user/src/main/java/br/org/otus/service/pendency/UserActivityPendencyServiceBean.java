package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.persistence.pendency.UserActivityPendencyDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import java.util.List;

public class UserActivityPendencyServiceBean implements UserActivityPendencyService {

  @Inject
  private UserActivityPendencyDao userActivityPendencyDao;


  @Override
  public void create(UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException {
    throw new NotImplementedException();
  }

  @Override
  public void update(UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException {
    throw new NotImplementedException();
  }

  @Override
  public UserActivityPendency getByActivityId(Long activityId) throws DataNotFoundException {
    throw new NotImplementedException();
  }

  @Override
  public List<UserActivityPendency> list() {
    throw new NotImplementedException();
//    List<UserActivityPendency> userActivityPendencies = new ArrayList<>();
//    //TODO
//    return userActivityPendencies;
  }

  @Override
  public void delete(UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException {
    throw new NotImplementedException();
  }
}
