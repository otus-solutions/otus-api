package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.persistence.pendency.UserActivityPendencyDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserActivityPendencyServiceBean implements UserActivityPendencyService {

  @Inject
  private UserActivityPendencyDao userActivityPendencyDao;

  public UserActivityPendencyServiceBean() {
    super();
  }

  @Override
  public ObjectId create(UserActivityPendency userActivityPendency, String userEmail) throws ValidationException, DataNotFoundException {
    userActivityPendency.setRequester(userEmail);
    return userActivityPendencyDao.create(userActivityPendency);
  }

  @Override
  public void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException {
    userActivityPendencyDao.update(userActivityPendencyOID, userActivityPendency);
  }

  @Override
  public void delete(ObjectId userActivityPendencyOID) throws ValidationException, DataNotFoundException {
    userActivityPendencyDao.delete(userActivityPendencyOID);
  }

  @Override
  public UserActivityPendency getByActivityId(String activityId) throws DataNotFoundException {
    return userActivityPendencyDao.findByActivityInfo(new ObjectId(activityId));
  }

  @Override
  public List<UserActivityPendency> listAllPendencies() throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findAllPendencies();
  }

  @Override
  public List<UserActivityPendency> listOpenedPendencies() throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findOpenedPendencies();
  }

  @Override
  public List<UserActivityPendency> listDonePendencies() throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findDonePendencies();
  }

}
