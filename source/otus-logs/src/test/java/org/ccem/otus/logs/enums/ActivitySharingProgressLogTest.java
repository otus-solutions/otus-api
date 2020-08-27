package org.ccem.otus.logs.enums;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ActivitySharingProgressLogTest {

  private ActivitySharingProgressLog activitySharingProgressLog;

  @Test
  public void get_method_should_return_activitySharingProgressLog_with_values_expected() {
    Assert.assertEquals("create", activitySharingProgressLog.CREATE.getValue());
    Assert.assertEquals("renew", activitySharingProgressLog.RENEW.getValue());
    Assert.assertEquals("access", activitySharingProgressLog.ACCESS.getValue());
    Assert.assertEquals("search", activitySharingProgressLog.SEARCH.getValue());
    Assert.assertEquals("deletion", activitySharingProgressLog.DELETION.getValue());
  }
}