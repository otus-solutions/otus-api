package org.ccem.otus.logs.service;

import org.bson.types.ObjectId;
import org.ccem.otus.logs.activity.ActivityLog;
import org.ccem.otus.logs.enums.ActivitySharingProgressLog;
import org.ccem.otus.logs.persistence.LogsActivitySharingDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class LogsActivitySharingServiceBean implements LogsActivitySharingService {
  @Inject
  private LogsActivitySharingDao logsActivitySharingDao;

  private ActivityLog activityLog;

  @Override
  public void logsActivitySharingCreate(ObjectId userId) {
    activityLog = new ActivityLog(ActivitySharingProgressLog.CREATE.getValue(),userId);
    logsActivitySharingDao.persist(activityLog);
  }

  @Override
  public void logsActivitySharingRenew(ObjectId userId) {
    activityLog = new ActivityLog(ActivitySharingProgressLog.RENEW.getValue(), userId);
    logsActivitySharingDao.persist(activityLog);
  }

  @Override
  public void logsActivitySharingAccess(ObjectId userId) {
    activityLog = new ActivityLog(ActivitySharingProgressLog.ACCESS.getValue(), userId);
    logsActivitySharingDao.persist(activityLog);
  }

  @Override
  public void logsActivitySharingSearch(ObjectId userId) {
    activityLog = new ActivityLog(ActivitySharingProgressLog.SEARCH.getValue(), userId);
    logsActivitySharingDao.persist(activityLog);
  }

  @Override
  public void logsActivitySharingDeletion(ObjectId userId) {
    activityLog = new ActivityLog(ActivitySharingProgressLog.DELETION.getValue(), userId);
    logsActivitySharingDao.persist(activityLog);
  }
}
