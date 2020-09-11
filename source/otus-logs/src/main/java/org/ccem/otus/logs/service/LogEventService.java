package org.ccem.otus.logs.service;

import org.ccem.otus.logs.events.ActivitySharedLog;

public interface LogEventService {
  void log(ActivitySharedLog activitySharedLog);
}
