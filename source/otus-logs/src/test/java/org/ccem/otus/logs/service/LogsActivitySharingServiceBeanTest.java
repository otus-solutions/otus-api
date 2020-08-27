package org.ccem.otus.logs.service;

import org.bson.types.ObjectId;
import org.ccem.otus.logs.activity.ActivityLog;
import org.ccem.otus.logs.persistence.LogsActivitySharingDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class LogsActivitySharingServiceBeanTest {
  private static final String ID = "5e0658135b4ff40f8916d2b5";

  @InjectMocks
  private LogsActivitySharingServiceBean logsActivitySharingServiceBean;

  @Mock
  private LogsActivitySharingDao logsActivitySharingDao;

  @Mock
  private ActivityLog activityLog;

  private ObjectId objectId = new ObjectId(ID);

  @Test
  public void testLogsActivitySharingCreateMethod_should_persist_Create() {
    logsActivitySharingServiceBean.logsActivitySharingCreate(objectId);
    verify(logsActivitySharingDao, times(1)).persist(any());
  }

  @Test
  public void testLogsActivitySharingRenewMethod_should_persist_Renew() {
    logsActivitySharingServiceBean.logsActivitySharingRenew(objectId);
    verify(logsActivitySharingDao, times(1)).persist(any());
  }

  @Test
  public void testLogsActivitySharingAccessMethod_should_persist_Access() {
    logsActivitySharingServiceBean.logsActivitySharingAccess(objectId);
    verify(logsActivitySharingDao, times(1)).persist(any());
  }

  @Test
  public void testLogsActivitySharingSearchMethod_should_persist_Search() {
    logsActivitySharingServiceBean.logsActivitySharingSearch(objectId);
    verify(logsActivitySharingDao, times(1)).persist(any());
  }

  @Test
  public void testLogsActivitySharingDeletionMethod_should_persist_Deletion() {
    logsActivitySharingServiceBean.logsActivitySharingDeletion(objectId);
    verify(logsActivitySharingDao, times(1)).persist(any());
  }
}