package br.org.otus.persistence.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import java.util.List;

public interface UserActivityPendencyDao {

  ObjectId create(UserActivityPendency userActivityPendency);

  void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws DataNotFoundException;

  void delete(ObjectId oid) throws DataNotFoundException;

  UserActivityPendency findByActivityOID(ObjectId activityOID) throws DataNotFoundException;

  List<UserActivityPendency> findAllPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> findOpenedPendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendency> findDonePendenciesToReceiver(String receiverEmail) throws DataNotFoundException, MemoryExcededException;

}
