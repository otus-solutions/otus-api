package org.ccem.otus.logs.service;

import org.bson.types.ObjectId;

public interface LogsActivitySharingService {
  void logsActivitySharingCreate(ObjectId userId);

  void logsActivitySharingRenew(ObjectId userId);

  void logsActivitySharingAccess(ObjectId userId);

  void logsActivitySharingSearch(ObjectId userId);

  void logsActivitySharingDeletion(ObjectId userId);
}
