package org.ccem.otus.model.survey.activity.sharing;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.DateAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ActivitySharingTest {

  private static final ObjectId OID = new ObjectId("5e13997795818e14a91a5268");
  private static final String OBJECT_TYPE = "ActivitySharing";
  private static final String ACTIVITY_ID = "5c7400d2d767afded0d84dcf";
  private static final ObjectId ACTIVITY_OID = new ObjectId(ACTIVITY_ID);
  private static final ObjectId USER_OID = new ObjectId("5c7400d2d767afded0d84ece");
  private static final String PARTICIPANT_TOKEN = "abc";

  private ActivitySharing activitySharing = new ActivitySharing(ACTIVITY_OID, USER_OID, PARTICIPANT_TOKEN);

  @Before
  public void setUp(){
    Whitebox.setInternalState(activitySharing, "id", OID);
  }

  @Test
  public void unitTest_for_invoke_getters() throws ParseException {
    assertEquals(OID, activitySharing.getId());
    assertEquals(OBJECT_TYPE, activitySharing.getObjectType());
    assertEquals(ACTIVITY_OID, activitySharing.getActivityId());
    assertEquals(PARTICIPANT_TOKEN, activitySharing.getParticipantToken());
    assertEquals(USER_OID, activitySharing.getUserId());

    String today = DateAdapter.nowToISODate();
    assertTrue(DateAdapter.compareDateWithoutTime(today,  activitySharing.getCreationDate()));
    assertTrue(DateAdapter.compareDateWithoutTime(
      plusExpirationTime(today),
      activitySharing.getExpirationDate()));
  }

  @Test
  public void serializeStaticMethod_should_convert_objectModel_to_JsonString() {
    assertTrue(ActivitySharing.serialize(activitySharing) instanceof String);
  }

  @Test
  public void deserializeStaticMethod_shold_convert_JsonString_to_objectModel() {
    String activitySharingJson = ActivitySharing.serialize(activitySharing);
    assertTrue(ActivitySharing.deserialize(activitySharingJson) instanceof ActivitySharing);
  }

  @Test
  public void renovate_method_should_add_EXPIRATION_TIME_to_expirationDate() throws ParseException {
    assertTrue(DateAdapter.compareDateWithoutTime(
      plusExpirationTime(DateAdapter.nowToISODate()),
      activitySharing.getExpirationDate()
    ));
  }

  private String plusExpirationTime(String dateStr) throws ParseException {
    return DateAdapter.getDatePlusDays(dateStr, ActivitySharing.EXPIRATION_TIME);
  }

}
