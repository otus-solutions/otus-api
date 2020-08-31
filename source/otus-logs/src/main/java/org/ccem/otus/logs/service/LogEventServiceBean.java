package org.ccem.otus.logs.service;

import org.ccem.otus.logs.activity.ActivityLog;
import org.ccem.otus.logs.events.ActivitySharedLog;
import org.ccem.otus.logs.persistence.LogsActivitySharingDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class LogEventServiceBean implements LogEventService {
  @Inject
  private LogsActivitySharingDao logsActivitySharingDao;

  @Override
  public void log(ActivitySharedLog activitySharedLog) {
    ActivityLog activityLog = new ActivityLog(activitySharedLog);
    logsActivitySharingDao.persist(activityLog);
  }
}
