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

  List<UserActivityPendency> listAllPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listOpenedPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listDonePendencies(String userEmail) throws DataNotFoundException, MemoryExcededException;
}