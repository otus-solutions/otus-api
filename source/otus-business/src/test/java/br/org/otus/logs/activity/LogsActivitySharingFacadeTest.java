package br.org.otus.logs.activity;

import org.bson.types.ObjectId;
import org.ccem.otus.logs.activity.ActivityLog;
import org.ccem.otus.logs.service.LogsActivitySharingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class LogsActivitySharingFacadeTest{
  private static final String ID = "5e0658135b4ff40f8916d2b5";

  @InjectMocks
  private LogsActivitySharingFacade logsActivitySharingFacade;

  @Mock
  private LogsActivitySharingService logsActivitysharingService;

  private ObjectId objectId = new ObjectId(ID);

  @Test
  public void testLogsActivitySharingCreateMethod_should_persist_Create() {
    logsActivitySharingFacade.logsActivitySharingCreate(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingCreate(any());
  }

  @Test
  public void testLogsActivitySharingRenewMethod_should_persist_Renew() {
    logsActivitySharingFacade.logsActivitySharingRenew(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingRenew(any());
  }

  @Test
  public void testLogsActivitySharingAccessMethod_should_persist_Access() {
    logsActivitySharingFacade.logsActivitySharingAccess(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingAccess(any());
  }

  @Test
  public void testLogsActivitySharingSearchMethod_should_persist_Search() {
    logsActivitySharingFacade.logsActivitySharingSearch(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingSearch(any());
  }

  @Test
  public void testLogsActivitySharingDeletionMethod_should_persist_Deletion() {
    logsActivitySharingFacade.logsActivitySharingDeletion(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingDeletion(any());
  }
}