package org.ccem.otus.logs.persistence;

import org.ccem.otus.logs.activity.ActivityLog;

public interface LogsActivitySharingDao {
  void persist(ActivityLog activityLog);
}
