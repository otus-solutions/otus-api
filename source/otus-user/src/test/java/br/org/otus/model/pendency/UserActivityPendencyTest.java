package br.org.otus.model.pendency;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyTest {

  private static final ObjectId OID = new ObjectId("5e13997795818e14a91a5268");
  private static final ObjectId OTHER_OID = new ObjectId("5e13997795818e14a91a5267");
  private static final String OBJECT_TYPE = "userActivityPendency";
  private static final String CREATION_DATE = "2019-12-30T19:31:08.570Z";
  private static final String DUE_DATE = "2019-11-20T19:31:08.570Z";
  private static final String REQUESTER_EMAIL = "requester@otus.com";
  private static final String RECEIVER_EMAIL = "receiver@otus.com";
  private static final String ANOTHER_USER_EMAIL = "user@otus.com";
  private static final String ACTIVITY_ID = "5c7400d2d767afded0d84dcf";
  private static final ObjectId ACTIVITY_OID = new ObjectId(ACTIVITY_ID);

  private UserActivityPendency userActivityPendency = new UserActivityPendency();
  private String userActivityPendencyJson;

  @Before
  public void setUp() {
    Whitebox.setInternalState(userActivityPendency, "_id", OID);
    Whitebox.setInternalState(userActivityPendency, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(userActivityPendency, "creationDate", CREATION_DATE);
    Whitebox.setInternalState(userActivityPendency, "dueDate", DUE_DATE);
    Whitebox.setInternalState(userActivityPendency, "requester", REQUESTER_EMAIL);
    Whitebox.setInternalState(userActivityPendency, "receiver", RECEIVER_EMAIL);
    Whitebox.setInternalState(userActivityPendency, "activityId", ACTIVITY_OID);

    userActivityPendencyJson = UserActivityPendency.serialize(userActivityPendency);
  }

  @Test
  public void unitTest_for_invoke_getters() {
    assertEquals(OID, userActivityPendency.getId());
    assertEquals(OBJECT_TYPE, userActivityPendency.getObjectType());
    assertEquals(CREATION_DATE, userActivityPendency.getCreationDate());
    assertEquals(DUE_DATE, userActivityPendency.getDueDate());
    assertEquals(REQUESTER_EMAIL, userActivityPendency.getRequester());
    assertEquals(RECEIVER_EMAIL, userActivityPendency.getReceiver());
    assertEquals(ACTIVITY_OID, userActivityPendency.getActivityId());
  }

  @Test
  public void unitTest_for_setRequester() {
    userActivityPendency.setRequester(ANOTHER_USER_EMAIL);
    assertEquals(ANOTHER_USER_EMAIL, userActivityPendency.getRequester());
  }

  @Test
  public void unitTest_for_setReceiver() {
    userActivityPendency.setReceiver(ANOTHER_USER_EMAIL);
    assertEquals(ANOTHER_USER_EMAIL, userActivityPendency.getReceiver());
  }

  @Test
  public void serializeStaticMethod_should_convert_objectModel_to_JsonString() {
    assertTrue(UserActivityPendency.serialize(userActivityPendency) instanceof String);
  }

  @Test
  public void deserializeStaticMethod_shold_convert_JsonString_to_objectModel() {
    assertTrue(UserActivityPendency.deserialize(userActivityPendencyJson) instanceof UserActivityPendency);
  }

  @Test
  public void getFrontGsonBuilder_method_should_return_GsonBuild_instance(){
    assertTrue(UserActivityPendency.getFrontGsonBuilder() instanceof GsonBuilder);
  }

  /*
   * Method equals
   */

  @Test
  public void objectModel_equals_himself(){
    assertTrue(userActivityPendency.equals(userActivityPendency));
  }

  @Test
  public void equals_method_return_TRUE(){
    assertTrue(userActivityPendency.equals(getUserActivityPendency(OID)));
  }

  @Test
  public void objectModel_not_equals_null_object(){
    assertFalse(userActivityPendency.equals(null));
  }

  @Test
  public void objectModel_not_equals_another_object_of_different_class(){
    assertFalse(userActivityPendency.equals(new Integer(0)));
  }

  @Test
  public void equals_method_return_FALSE_in_case_different_id(){
    assertFalse(userActivityPendency.equals(getUserActivityPendency(OTHER_OID)));
  }


  private UserActivityPendency getUserActivityPendency(ObjectId oid){
    UserActivityPendency userActivityPendency2 = new UserActivityPendency();
    Whitebox.setInternalState(userActivityPendency2, "_id", oid);
    return userActivityPendency2;
  }

}
