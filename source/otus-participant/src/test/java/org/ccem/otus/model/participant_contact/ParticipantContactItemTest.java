package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItemValueEmail;
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

  private ParticipantContactItem participantContactItem = new ParticipantContactItem();
  private ParticipantContactItemValue participantContactItemValue = PowerMockito.spy(new ParticipantContactItemValueEmail());

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContactItemValue, "value", "some string");
    Whitebox.setInternalState(participantContactItem, "value", participantContactItemValue);
    Whitebox.setInternalState(participantContactItem, "observation", OBSERVATION);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(participantContactItemValue, participantContactItem.getValue());
    assertEquals(OBSERVATION, participantContactItem.getObservation());
  }

  @Test
  public void getAllMyAttributes_should_return_HashMap_String_object_instance(){
    assertEquals(participantContactItemValue, participantContactItem.getAllMyAttributes().get("value"));
  }

  @Test
  public void getContactValueAttribute_should_return_HashMap_String_object_instance(){
    assertEquals(participantContactItemValue, participantContactItem.getContactValueAttribute().get("value"));
  }

//  @Test
//  public void serialize_static_method_should_convert_objectModel_to_JsonString(){
//    assertTrue(ParticipantContactItem.serialize(participantContactItem) instanceof String);
//  }
//
//  @Test
//  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
//    String participantContactItemJson = ParticipantContactItem.serialize(participantContactItem);
//    assertTrue(ParticipantContactItem.deserialize(participantContactItemJson) instanceof ParticipantContactItem);
//  }

  @Test
  public void isValid_method_should_return_TRUE(){
    assertTrue(participantContactItem.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_invalid_participantContactItemValue(){
    PowerMockito.when(participantContactItemValue.isValid()).thenReturn(false);
    assertFalse(participantContactItem.isValid());
  }

}
