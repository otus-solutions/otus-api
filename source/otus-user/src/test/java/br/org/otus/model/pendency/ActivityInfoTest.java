package br.org.otus.model.pendency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ActivityInfoTest {

  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final String ACRONYM = "ABCD";
  private static final String NAME = "Activity Name";
  private static final String LAST_STATUS_NAME = "CREATED";
  private static final String LAST_STATUS_DATE = "2000-01-01T10:00:00.000Z";
  private static final String EXTERNAL_ID = "0364AAA645";

  private ActivityInfo activityInfo = new ActivityInfo();
  private String activityInfoJson;

  @Before
  public void setUp() {
    Whitebox.setInternalState(activityInfo, "recruitmentNumber", RECRUITMENT_NUMBER);
    Whitebox.setInternalState(activityInfo, "acronym", ACRONYM);
    Whitebox.setInternalState(activityInfo, "name", NAME);
    Whitebox.setInternalState(activityInfo, "lastStatusName", LAST_STATUS_NAME);
    Whitebox.setInternalState(activityInfo, "lastStatusDate", LAST_STATUS_DATE);
    Whitebox.setInternalState(activityInfo, "externalID", EXTERNAL_ID);
    activityInfoJson = ActivityInfo.serialize(activityInfo);
  }

  @Test
  public void unitTest_for_invoke_getters() {
    assertEquals(RECRUITMENT_NUMBER, activityInfo.getRecruitmentNumber());
    assertEquals(ACRONYM, activityInfo.getAcronym());
    assertEquals(NAME, activityInfo.getName());
    assertEquals(EXTERNAL_ID, activityInfo.getExternalID());
    assertEquals(LAST_STATUS_NAME, activityInfo.getLastStatusName());
    assertEquals(LAST_STATUS_DATE, activityInfo.getLastStatusDate());
  }

  @Test
  public void serializeStaticMethod_should_convert_objectModel_to_JsonString() {
    assertTrue(activityInfo.serialize(activityInfo) instanceof String);
  }

  @Test
  public void deserializeStaticMethod_shold_convert_JsonString_to_objectModel() {
    assertTrue(activityInfo.deserialize(activityInfoJson) instanceof ActivityInfo);
  }

  /*
   * Method equals
   */

  @Test
  public void objectModel_equals_himself(){
    assertTrue(activityInfo.equals(activityInfo));
  }

  @Test
  public void equals_method_return_TRUE(){
    assertTrue(activityInfo.equals(getOtherActivityInfo(RECRUITMENT_NUMBER, ACRONYM)));
  }

  @Test
  public void objectModel_not_equals_null_object(){
    assertFalse(activityInfo.equals(null));
  }

  @Test
  public void objectModel_not_equals_another_object_of_different_class(){
    assertFalse(activityInfo.equals(new Integer(0)));
  }

  @Test
  public void equals_method_return_FALSE_in_case_different_recruitmentNumber(){
    assertFalse(activityInfo.equals(getOtherActivityInfo(RECRUITMENT_NUMBER+1, ACRONYM)));
  }

  @Test
  public void equals_method_return_FALSE_in_case_different_acronym(){
    assertFalse(activityInfo.equals(getOtherActivityInfo(RECRUITMENT_NUMBER, ACRONYM+"X")));
  }

  private ActivityInfo getOtherActivityInfo(Long rn, String acronym){
    ActivityInfo activityInfo2 = new ActivityInfo();
    Whitebox.setInternalState(activityInfo2, "recruitmentNumber", rn);
    Whitebox.setInternalState(activityInfo2, "acronym", acronym);
    Whitebox.setInternalState(activityInfo2, "name", NAME);
    Whitebox.setInternalState(activityInfo2, "lastStatusName", LAST_STATUS_NAME);
    Whitebox.setInternalState(activityInfo2, "lastStatusDate", LAST_STATUS_DATE);
    Whitebox.setInternalState(activityInfo2, "externalID", EXTERNAL_ID);
    return activityInfo2;
  }

}
