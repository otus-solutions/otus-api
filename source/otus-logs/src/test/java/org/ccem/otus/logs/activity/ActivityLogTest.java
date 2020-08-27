package org.ccem.otus.logs.activity;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ActivityLogTest {
  private static final String activityLogExpected = "{\"action\":\"create\",\"userId\":{\"$oid\":\"5e0658135b4ff40f8916d2b5\"},\"date\":\"2020-02-23T13:26:00Z\"}";
  private static final String ACTION = "create";
  private static final ObjectId USER_ID = new ObjectId("5e0658135b4ff40f8916d2b5");
  private static final LocalDateTime DATE = LocalDateTime.of(2020, 02, 23, 13, 26);

  @InjectMocks
  private ActivityLog activityLog;

  @Test
  public void testGetActionMethod_and_setActionMethod_should_return_string() {
    activityLog.setAction(ACTION);
    assertEquals(ACTION, activityLog.getAction());
  }

  @Test
  public void testGetUserIdMethod_and_setUserIdMethod_should_return_objectID() {
    activityLog.setUserId(USER_ID);
    assertEquals(USER_ID, activityLog.getUserId());
  }

  @Test
  public void testGetDateMethod_and_setDateMethod_should_return_localDateTime() {
    activityLog.setDate(DATE);
    assertEquals(DATE, activityLog.getDate());
  }

  @Test
  public void testSerializeMethod_should_convert_object_in_string() {
    activityLog = new ActivityLog(ACTION, USER_ID);
    activityLog.setDate(DATE);
    assertEquals(activityLogExpected, ActivityLog.serialize(activityLog));
  }

  @Test
  public void testDeserializeMethod_should_convert_to_activityLog() {
    assertTrue(ActivityLog.deserialize(activityLogExpected) instanceof ActivityLog);
  }
}