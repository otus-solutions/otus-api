package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface UserActivityPendencyService {

  void create(UserActivityPendency userActivityPendency, String userEmail) throws ValidationException, DataNotFoundException;

  void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException;

  void delete(ObjectId userActivityPendencyOID) throws ValidationException, DataNotFoundException;

  UserActivityPendency getByActivityId(String activityId) throws DataNotFoundException;

  List<UserActivityPendency> listAllPendencies() throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listOpenedPendencies() throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> listDonePendencies() throws DataNotFoundException, MemoryExcededException;
}