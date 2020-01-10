package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface UserActivityPendencyService {

  ObjectId create(String userEmail, String userActivityPendencyJson);

  void update(String userActivityPendencyId, String userActivityPendencyJson) throws DataNotFoundException;

  void delete(String userActivityPendencyId) throws DataNotFoundException;

  UserActivityPendency getByActivityId(String activityId) throws DataNotFoundException;

  List<UserActivityPendency> listAllPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listOpenedPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listDonePendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listAllPendenciesFromRequester(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listOpenedPendenciesFromRequester(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listDonePendenciesFromRequester(String receiverEmail) throws DataNotFoundException, MemoryExcededException;
}