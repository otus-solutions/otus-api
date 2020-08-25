package br.org.otus.logs.activity;

import org.bson.types.ObjectId;
import org.ccem.otus.logs.activity.ActivityLog;
import org.ccem.otus.logs.enums.ActivitySharingProgressLog;
import org.ccem.otus.logs.service.LogsActivitySharingService;

import javax.inject.Inject;

public class LogsActivitySharingFacade {
  @Inject
  private ActivityLog activityLog;
  @Inject
  private LogsActivitySharingService logsActivitySharingService;
  @Inject
  private ActivitySharingProgressLog activitySharingProgressLog;

  public void logsActivitySharingCreate(ObjectId userId) {
    activityLog = new ActivityLog(activitySharingProgressLog.CREATE.getValue(),userId);
    logsActivitySharingService.persist(activityLog);
  }

  public void logsActivitySharingRenew(ObjectId userId) {
    activityLog = new ActivityLog(activitySharingProgressLog.RENEW.getValue(), userId);
    logsActivitySharingService.persist(activityLog);
  }

  public void logsActivitySharingAccess(ObjectId userId) {
    activityLog = new ActivityLog(activitySharingProgressLog.ACCESS.getValue(), userId);
    logsActivitySharingService.persist(activityLog);
  }

  public void logsActivitySharingSearch(ObjectId userId) {
    activityLog = new ActivityLog(activitySharingProgressLog.SEARCH.getValue(), userId);
    logsActivitySharingService.persist(activityLog);
  }

  public void logsActivitySharingDeletion(ObjectId userId) {
    activityLog = new ActivityLog(activitySharingProgressLog.DELETION.getValue(), userId);
    logsActivitySharingService.persist(activityLog);
  }
}
