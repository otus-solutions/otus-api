package br.org.otus.logs;

import br.org.otus.logs.LogEventFacade;
import org.bson.types.ObjectId;
import org.ccem.otus.logs.events.ActivitySharedLog;
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

  private ActivitySharedLog activitySharedLog;

  private ObjectId objectId = new ObjectId(ID);

  @Test
  public void testLogMethod_should_persist_action() {
    logEventFacade.log(activitySharedLog);
    verify(logsActivitysharingService, times(1)).log(any());
  }

}