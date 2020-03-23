package org.ccem.otus.model.participant_contact;

import com.google.gson.internal.LinkedTreeMap;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.ccem.otus.participant.model.participant_contact.Email;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItemValue;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@PrepareForTest({ParticipantContactItem.class})
public class ParticipantContactItemTest {

  private static final String OBSERVATION = "obs";

  private ParticipantContactItem<Email> participantContactItem = new ParticipantContactItem<>();
  private ParticipantContactItemValue participantContactItemValue = PowerMockito.spy(new Email());

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContactItem, "value", participantContactItemValue);
    Whitebox.setInternalState(participantContactItem, "observation", OBSERVATION);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(participantContactItemValue, participantContactItem.getValue());
    assertEquals(OBSERVATION, participantContactItem.getObservation());
  }

  @Test
  public void test_setValue_method(){
    Email participantContactItemValue2 = new Email();
    participantContactItem.setValue(participantContactItemValue2);
    assertEquals(participantContactItemValue2, participantContactItem.getValue());
  }

  @Test
  public void test_setFromLinkedTreeMap_method() {
    Email participantContactItemValue2 = new Email();
    LinkedTreeMap<String, Object> valueMap = new LinkedTreeMap();
    valueMap.put("content", participantContactItemValue2.getContent());
    final String OBSERVATION2 = OBSERVATION+"2";

    LinkedTreeMap<String, Object> map = new LinkedTreeMap();
    map.put("value", valueMap);
    map.put("observation", OBSERVATION2);

    participantContactItem.setFromLinkedTreeMap(map);
    assertEquals(OBSERVATION2, participantContactItem.getObservation());
  }

  @Test
  public void serialize_static_method_should_convert_objectModel_to_JsonString(){
    assertTrue(ParticipantContactItem.serialize(new ParticipantContactItem<Email>()) instanceof String);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    String participantContactItemJson = ParticipantContactItem.serialize(new ParticipantContactItem<Email>());
    assertTrue(ParticipantContactItem.deserialize(participantContactItemJson) instanceof ParticipantContactItem);
  }

  @Test
  public void isValid_method_should_return_TRUE(){
    PowerMockito.when(participantContactItemValue.isValid()).thenReturn(true);
    assertTrue(participantContactItem.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE(){
    assertFalse(participantContactItem.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_participantContactItemValue(){
    Whitebox.setInternalState(participantContactItem, "value", (ParticipantContactItem<Email>)null);
    assertFalse(participantContactItem.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_invalid_participantContactItemValue(){
    PowerMockito.when(participantContactItemValue.isValid()).thenReturn(false);
    assertFalse(participantContactItem.isValid());
  }

}
