package br.org.otus.logs.activity;

import org.bson.types.ObjectId;
import org.ccem.otus.logs.service.LogsActivitySharingService;

import javax.inject.Inject;

public class LogsActivitySharingFacade {
  @Inject
  private LogsActivitySharingService logsActivitySharingService;

  public void logsActivitySharingCreate(ObjectId userId) {
    logsActivitySharingService.logsActivitySharingCreate(userId);
  }

  public void logsActivitySharingRenew(ObjectId userId) {
    logsActivitySharingService.logsActivitySharingRenew(userId);
  }

  public void logsActivitySharingAccess(ObjectId userId) {
    logsActivitySharingService.logsActivitySharingAccess(userId);
  }

  public void logsActivitySharingSearch(ObjectId userId) {
    logsActivitySharingService.logsActivitySharingSearch(userId);
  }

  public void logsActivitySharingDeletion(ObjectId userId) {
    logsActivitySharingService.logsActivitySharingDeletion(userId);
  }
}