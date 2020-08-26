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
  private LogsActivitySharingServiceBean logsActivitysharingServiceBean;

  @Mock
  private LogsActivitySharingDao logsActivitySharingDao;

  @Mock
  private ActivityLog activityLog;

  private ObjectId objectId = new ObjectId(ID);

  @Test
  public void testPersistMethod_should_persist_actions() {
    logsActivitysharingServiceBean.persist(activityLog);
    verify(logsActivitySharingDao, times(1)).persist(any());
  }
}