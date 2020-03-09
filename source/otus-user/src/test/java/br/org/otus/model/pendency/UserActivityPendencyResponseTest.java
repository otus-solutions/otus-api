package br.org.otus.model.pendency;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyResponseTest {

  private static final ObjectId OID = new ObjectId("5e13997795818e14a91a5268");
  private static final String OBJECT_TYPE = "userActivityPendencyResponse";
  private static final String CREATION_DATE = "2019-12-30T19:31:08.570Z";
  private static final String DUE_DATE = "2019-11-20T19:31:08.570Z";
  private static final String REQUESTER_EMAIL = "requester@otus.com";
  private static final String RECEIVER_EMAIL = "receiver@otus.com";
  private static final String ANOTHER_USER_EMAIL = "user@otus.com";
  private static final ObjectId ACTIVITY_OID = new ObjectId("5c7400d2d767afded0d84dcf");

  private UserActivityPendencyResponse userActivityPendencyResponse = new UserActivityPendencyResponse();
  private ActivityInfo activityInfo;
  private String userActivityPendencyResponseJson;

  @Before
  public void setUp() {
    Whitebox.setInternalState(userActivityPendencyResponse, "_id", OID);
    Whitebox.setInternalState(userActivityPendencyResponse, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(userActivityPendencyResponse, "creationDate", CREATION_DATE);
    Whitebox.setInternalState(userActivityPendencyResponse, "dueDate", DUE_DATE);
    Whitebox.setInternalState(userActivityPendencyResponse, "requester", REQUESTER_EMAIL);
    Whitebox.setInternalState(userActivityPendencyResponse, "receiver", RECEIVER_EMAIL);
    Whitebox.setInternalState(userActivityPendencyResponse, "activityId", ACTIVITY_OID);

    activityInfo = new ActivityInfo();
    Whitebox.setInternalState(userActivityPendencyResponse, "activityInfo", activityInfo);

    userActivityPendencyResponseJson = UserActivityPendencyResponse.serialize(userActivityPendencyResponse);
  }

  @Test
  public void unitTest_for_invoke_getters() {
    assertEquals(OID, userActivityPendencyResponse.getId());
    assertEquals(OBJECT_TYPE, userActivityPendencyResponse.getObjectType());
    assertEquals(CREATION_DATE, userActivityPendencyResponse.getCreationDate());
    assertEquals(DUE_DATE, userActivityPendencyResponse.getDueDate());
    assertEquals(REQUESTER_EMAIL, userActivityPendencyResponse.getRequester());
    assertEquals(RECEIVER_EMAIL, userActivityPendencyResponse.getReceiver());
    assertEquals(ACTIVITY_OID, userActivityPendencyResponse.getActivityId());
    assertEquals(activityInfo, userActivityPendencyResponse.getActivityInfo());
  }

  @Test
  public void unitTest_for_setRequester() {
    userActivityPendencyResponse.setRequester(ANOTHER_USER_EMAIL);
    assertEquals(ANOTHER_USER_EMAIL, userActivityPendencyResponse.getRequester());
  }

  @Test
  public void unitTest_for_setReceiver() {
    userActivityPendencyResponse.setReceiver(ANOTHER_USER_EMAIL);
    assertEquals(ANOTHER_USER_EMAIL, userActivityPendencyResponse.getReceiver());
  }

  @Test
  public void serializeStaticMethod_should_convert_objectModel_to_JsonString() {
    assertTrue(UserActivityPendencyResponse.serialize(userActivityPendencyResponse) instanceof String);
  }

  @Test
  public void deserializeStaticMethod_shold_convert_JsonString_to_objectModel() {
    assertTrue(UserActivityPendencyResponse.deserialize(userActivityPendencyResponseJson) instanceof UserActivityPendencyResponse);
  }

  @Test
  public void getFrontGsonBuilder_method_should_return_GsonBuild_instance(){
    assertTrue(UserActivityPendencyResponse.getFrontGsonBuilder() instanceof GsonBuilder);
  }

}
