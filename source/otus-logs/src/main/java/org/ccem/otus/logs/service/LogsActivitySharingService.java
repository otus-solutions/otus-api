package org.ccem.otus.logs.service;

import org.ccem.otus.logs.activity.ActivityLog;

public interface LogsActivitySharingService {
  void persist(ActivityLog activityLog);
}
