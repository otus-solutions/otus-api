package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.model.pendency.UserActivityPendencyResponse;
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

  List<UserActivityPendencyResponse> listAllPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> listOpenedPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> listDonePendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> listAllPendenciesFromRequester(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> listOpenedPendenciesFromRequester(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> listDonePendenciesFromRequester(String receiverEmail) throws DataNotFoundException, MemoryExcededException;
}