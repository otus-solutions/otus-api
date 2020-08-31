package br.org.otus.logs;

import org.ccem.otus.logs.events.ActivitySharedLog;
import org.ccem.otus.logs.service.LogEventService;

import javax.inject.Inject;

public class LogEventFacade {
  @Inject
  private LogEventService logEventService;

  public void log(ActivitySharedLog activitySharedLog) {
    logEventService.log(activitySharedLog);
  }
}