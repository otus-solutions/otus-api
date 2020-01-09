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
  public ObjectId create(String userEmail, String userActivityPendencyJson) {
    UserActivityPendency userActivityPendency = UserActivityPendency.deserialize(userActivityPendencyJson);
    userActivityPendency.setRequester(userEmail);
    return userActivityPendencyDao.create(userActivityPendency);
  }

  @Override
  public void update(String userActivityPendencyId, String userActivityPendencyJson) throws DataNotFoundException {
    userActivityPendencyDao.update(
      new ObjectId(userActivityPendencyId),
      UserActivityPendency.deserialize(userActivityPendencyJson));
  }

  @Override
  public void delete(String userActivityPendencyId) throws DataNotFoundException {
    userActivityPendencyDao.delete(new ObjectId(userActivityPendencyId));
  }

  @Override
  public UserActivityPendency getByActivityId(String activityId) throws DataNotFoundException {
    return userActivityPendencyDao.findByActivityOID(new ObjectId(activityId));
  }

  @Override
  public List<UserActivityPendency> listAllPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findAllPendenciesToReceiver(receiverEmail);
  }

  @Override
  public List<UserActivityPendency> listOpenedPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findOpenedPendenciesToReceiver(receiverEmail);
  }

  @Override
  public List<UserActivityPendency> listDonePendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findDonePendenciesToReceiver(receiverEmail);
  }

}
