package org.ccem.otus.logs.service;

import org.ccem.otus.logs.activity.ActivityLog;
import org.ccem.otus.logs.persistence.LogsActivitySharingDao;

import javax.inject.Inject;

public class LogsActivitySharingServiceBean implements LogsActivitySharingService {
  @Inject
  private LogsActivitySharingDao logsActivitySharingDao;

  public void persist(ActivityLog activityLog) {
    logsActivitySharingDao.persist(activityLog);
  }
}
