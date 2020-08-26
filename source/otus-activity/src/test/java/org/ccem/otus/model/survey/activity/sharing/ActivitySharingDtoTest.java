package org.ccem.otus.model.survey.activity.sharing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ActivitySharingDtoTest {

  private static final String SHARED_URL = "https://otus";

  private ActivitySharingDto activitySharingDto;
  private ActivitySharing activitySharing;

  @Before
  public void setup(){
    activitySharing = new ActivitySharing();
    activitySharingDto  = new ActivitySharingDto(activitySharing, SHARED_URL);
  }

  @Test
  public void unitTest_for_invoke_getters(){
    assertEquals(activitySharing, activitySharingDto.getActivitySharing());
    assertEquals(SHARED_URL, activitySharingDto.getUrl());
  }

  @Test
  public void isValid_method_should_return_true(){
    assertTrue(activitySharingDto.isValid());
  }

  @Test
  public void isValid_method_should_return_falsein_case_null_activitySharing(){
    Whitebox.setInternalState(activitySharingDto, "activitySharing", (ActivitySharing)null);
    assertFalse(activitySharingDto.isValid());
  }

  @Test
  public void isValid_method_should_return_falsein_case_null_url(){
    Whitebox.setInternalState(activitySharingDto, "url", (String)null);
    assertFalse(activitySharingDto.isValid());
  }


  @Test
  public void serialize_static_method_should_convert_objectModel_to_JsonString() {
    assertTrue(ActivitySharingDto.serialize(activitySharingDto) instanceof String);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel() {
    String activitySharingJson = ActivitySharingDto.serialize(activitySharingDto);
    assertTrue(ActivitySharingDto.deserialize(activitySharingJson) instanceof ActivitySharingDto);
  }

  @Test
  public void getFrontGsonBuilder_test() {
    assertNotNull(ActivitySharingDto.getFrontGsonBuilder());
  }
}
