package org.ccem.otus.logs.service;

import org.bson.types.ObjectId;
import org.ccem.otus.logs.events.ActivitySharedLog;
import org.ccem.otus.logs.persistence.LogsActivitySharingDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class LogEventBeanTest {
  private static final String ID = "5e0658135b4ff40f8916d2b5";

  @InjectMocks
  private LogEventServiceBean logEventServiceBean;

  @Mock
  private LogsActivitySharingDao logsActivitySharingDao;

  @Mock
  private ActivitySharedLog activitySharedLog;

  private ObjectId objectId = new ObjectId(ID);

  @Test
  public void testLogsActivitySharingCreateMethod_should_persist_Create() {
    logEventServiceBean.log(activitySharedLog);
    verify(logsActivitySharingDao, times(1)).persist(any());
  }
}