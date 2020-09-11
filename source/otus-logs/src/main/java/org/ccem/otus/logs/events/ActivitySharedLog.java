package org.ccem.otus.logs.events;

import org.bson.types.ObjectId;
import org.ccem.otus.logs.enums.ActivitySharingProgressLog;

public class ActivitySharedLog implements LogEvent {
  private ObjectId objectId;
  private ActivitySharingProgressLog activitySharingProgressLog;

  public ActivitySharedLog(ObjectId objectId, ActivitySharingProgressLog activitySharingProgressLog) {
    this.objectId = objectId;
    this.activitySharingProgressLog = activitySharingProgressLog;
  }

  public ObjectId getObjectId() {
    return objectId;
  }

  public String getActivitySharingProgressLog() {
    return activitySharingProgressLog.toString();
  }
}
