package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.persistence.pendency.UserActivityPendencyDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

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
  public ObjectId create(String userEmail, UserActivityPendency userActivityPendency) {
    userActivityPendency.setRequester(userEmail);
    return userActivityPendencyDao.create(userActivityPendency);
  }

  @Override
  public void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws DataNotFoundException {
    userActivityPendencyDao.update(userActivityPendencyOID, userActivityPendency);
  }

  @Override
  public void delete(ObjectId userActivityPendencyOID) throws DataNotFoundException {
    userActivityPendencyDao.delete(userActivityPendencyOID);
  }

  @Override
  public UserActivityPendency getByActivityId(String activityId) throws DataNotFoundException {
    return userActivityPendencyDao.findByActivityOID(new ObjectId(activityId));
  }

  @Override
  public List<UserActivityPendency> listAllPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findAllPendencies(userEmail);
  }

  @Override
  public List<UserActivityPendency> listOpenedPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findOpenedPendencies(userEmail);
  }

  @Override
  public List<UserActivityPendency> listDonePendencies(String userEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findDonePendencies(userEmail);
  }

}
