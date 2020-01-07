package br.org.otus.model.pendency;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ActivityInfoTest {

  private static final ObjectId ACTIVITY_OID = new ObjectId("5c7400d2d767afded0d84dcf");
  private static final String ACTIVITY_ACRONYM = "ABCD";
  private static final int ACTIVITY_RECRUITMENT_NUMBER = 1234567;

  private ActivityInfo activityInfo = new ActivityInfo();
  private String activityInfoJson;

  @Before
  public void setUp(){
    Whitebox.setInternalState(activityInfo, "id", ACTIVITY_OID);
    Whitebox.setInternalState(activityInfo, "acronym", ACTIVITY_ACRONYM);
    Whitebox.setInternalState(activityInfo, "recruitmentNumber", ACTIVITY_RECRUITMENT_NUMBER);
    activityInfoJson = ActivityInfo.serialize(activityInfo);
  }

  @Test
  public void unitTest_for_invoke_getters(){
    assertEquals(ACTIVITY_OID, activityInfo.getId());
    assertEquals(ACTIVITY_ACRONYM, activityInfo.getAcronym());
    assertEquals(ACTIVITY_RECRUITMENT_NUMBER, activityInfo.getRecruitmentNumber());
  }

  @Test
  public void serializeStaticMethod_should_convert_objectModel_to_JsonString() {
    assertTrue(activityInfo.serialize(activityInfo) instanceof String);
  }

  @Test
  public void deserializeStaticMethod_shold_convert_JsonString_to_objectModel() {
    assertTrue(activityInfo.deserialize(activityInfoJson) instanceof ActivityInfo);
  }

}
