package org.ccem.otus.model.survey.activity.permission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class ActivityAccessPermissionTest {

  private static final Integer VERSION = 1;
  private static final String ACRONYM = "RFC";
  private static final Object USER_EMAIL1 = "otus@gmail.com";
  private static final Object OBJECT_TYPE = "ActivityAccessPermission";

  private ActivityAccessPermission activityAccessPermission = new ActivityAccessPermission();
  private ObjectId objectId;
  private List<Object> exclusiveDisjunction;
  private String activityAccessPermissionJson;

  @Before
  public void setUp() {
    objectId = new ObjectId();
    exclusiveDisjunction = Arrays.asList(USER_EMAIL1);
    Whitebox.setInternalState(activityAccessPermission, "_id", objectId);
    Whitebox.setInternalState(activityAccessPermission, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(activityAccessPermission, "version", VERSION);
    Whitebox.setInternalState(activityAccessPermission, "acronym", ACRONYM);
    Whitebox.setInternalState(activityAccessPermission, "exclusiveDisjunction", exclusiveDisjunction);

    activityAccessPermissionJson = ActivityAccessPermission.serialize(activityAccessPermission);
  }

  @Test
  public void unitTest_for_evoke_getters() {
    assertEquals(VERSION, activityAccessPermission.getVersion());
    assertEquals(objectId, activityAccessPermission.getId());
    assertEquals(ACRONYM, activityAccessPermission.getAcronym());
    assertEquals(USER_EMAIL1, activityAccessPermission.getExclusiveDisjunction().get(0));
  }

  @Test
  public void serializeStaticMethod_should_convert_objectModel_to_JsonString() {
    assertTrue(ActivityAccessPermission.serialize(activityAccessPermission) instanceof String);
  }

  @Test
  public void deserializeStaticMethod_shold_convert_JsonString_to_objectModel() {
    assertTrue(ActivityAccessPermission.deserialize(activityAccessPermissionJson) instanceof ActivityAccessPermission);
  }
}
