package br.org.otus.persistence.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.model.pendency.UserActivityPendencyResponse;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import java.util.List;

public interface UserActivityPendencyDao {

  ObjectId create(UserActivityPendency userActivityPendency);

  void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws DataNotFoundException;

  void delete(ObjectId oid) throws DataNotFoundException;

  UserActivityPendency findByActivityOID(ObjectId activityOID) throws DataNotFoundException;

  List<UserActivityPendencyResponse> getAllPendencies(UserActivityPendencyDto userActivityPendencyDto) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> findAllPendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> findOpenedPendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> findDonePendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> findAllPendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> findOpenedPendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException;

  List<UserActivityPendencyResponse> findDonePendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException;

}
