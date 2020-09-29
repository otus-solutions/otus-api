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

  private static final ObjectId OBJECT_ID = new ObjectId();
  private static final String ACRONYM = "ABC";
  private static final Integer VERSION = 1;
  private static final Object USER_EMAIL = "otus@gmail.com";
  private static final Object OBJECT_TYPE = "ActivityAccessPermission";

  private ActivityAccessPermission activityAccessPermission = new ActivityAccessPermission();
  private List<Object> exclusiveDisjunction;
  private String activityAccessPermissionJson;

  @Before
  public void setUp() {
    exclusiveDisjunction = Arrays.asList(USER_EMAIL);
    Whitebox.setInternalState(activityAccessPermission, "_id", OBJECT_ID);
    Whitebox.setInternalState(activityAccessPermission, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(activityAccessPermission, "acronym", ACRONYM);
    Whitebox.setInternalState(activityAccessPermission, "version", VERSION);
    Whitebox.setInternalState(activityAccessPermission, "exclusiveDisjunction", exclusiveDisjunction);

    activityAccessPermissionJson = ActivityAccessPermission.serialize(activityAccessPermission);
  }

  @Test
  public void unitTest_for_evoke_getters() {
    assertEquals(VERSION, activityAccessPermission.getVersion());
    assertEquals(OBJECT_ID, activityAccessPermission.getId());
    assertEquals(ACRONYM, activityAccessPermission.getAcronym());
    assertEquals(USER_EMAIL, activityAccessPermission.getExclusiveDisjunction().get(0));
  }

  @Test
  public void unitTest_for_setVersion_method(){
    final Integer NEW_VERSION = VERSION + 1;
    activityAccessPermission.setVersion(NEW_VERSION);
    assertEquals(NEW_VERSION, activityAccessPermission.getVersion());
  }

  @Test
  public void unitTest_for_setExclusiveDisjunction_method(){
    final List<String> NEW_EXCLUSIVE_DISJUNCTION = Arrays.asList(new String[]{USER_EMAIL +".br"});
    activityAccessPermission.setExclusiveDisjunction(NEW_EXCLUSIVE_DISJUNCTION);
    assertEquals(NEW_EXCLUSIVE_DISJUNCTION, activityAccessPermission.getExclusiveDisjunction());
  }

  @Test
  public void unitTest_for_constructor_with_parameters() {
    ActivityAccessPermission activityAccessPermission2 = new ActivityAccessPermission(ACRONYM, VERSION);
    assertEquals(ACRONYM, activityAccessPermission.getAcronym());
    assertEquals(VERSION, activityAccessPermission.getVersion());
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
