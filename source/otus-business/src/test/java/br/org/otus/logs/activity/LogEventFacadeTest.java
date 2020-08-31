package br.org.otus.logs.activity;

import br.org.otus.logs.LogEventFacade;
import org.bson.types.ObjectId;
import org.ccem.otus.logs.service.LogEventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class LogEventFacadeTest {
  private static final String ID = "5e0658135b4ff40f8916d2b5";

  @InjectMocks
  private LogEventFacade logEventFacade;

  @Mock
  private LogEventService logsActivitysharingService;

  private ObjectId objectId = new ObjectId(ID);

  @Test
  public void testLogsActivitySharingCreateMethod_should_persist_Create() {
    logEventFacade.logsActivitySharingCreate(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingCreate(any());
  }

  @Test
  public void testLogsActivitySharingRenewMethod_should_persist_Renew() {
    logEventFacade.logsActivitySharingRenew(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingRenew(any());
  }

  @Test
  public void testLogsActivitySharingAccessMethod_should_persist_Access() {
    logEventFacade.logsActivitySharingAccess(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingAccess(any());
  }

  @Test
  public void testLogsActivitySharingSearchMethod_should_persist_Search() {
    logEventFacade.logsActivitySharingSearch(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingSearch(any());
  }

  @Test
  public void testLogsActivitySharingDeletionMethod_should_persist_Deletion() {
    logEventFacade.logsActivitySharingDeletion(objectId);
    verify(logsActivitysharingService, times(1)).logsActivitySharingDeletion(any());
  }
}