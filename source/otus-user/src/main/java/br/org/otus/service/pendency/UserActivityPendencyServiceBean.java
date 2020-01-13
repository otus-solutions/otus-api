package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.model.pendency.UserActivityPendencyResponse;
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
  public List<UserActivityPendencyResponse> listAllPendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findAllPendenciesToReceiver(receiverUserEmail);
  }

  @Override
  public List<UserActivityPendencyResponse> listOpenedPendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findOpenedPendenciesToReceiver(receiverUserEmail);
  }

  @Override
  public List<UserActivityPendencyResponse> listDonePendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findDonePendenciesToReceiver(receiverUserEmail);
  }

  @Override
  public List<UserActivityPendencyResponse> listAllPendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findAllPendenciesFromRequester(requesterUserEmail);
  }

  @Override
  public List<UserActivityPendencyResponse> listOpenedPendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findOpenedPendenciesFromRequester(requesterUserEmail);
  }

  @Override
  public List<UserActivityPendencyResponse> listDonePendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException {
    return userActivityPendencyDao.findDonePendenciesFromRequester(requesterUserEmail);
  }

}
