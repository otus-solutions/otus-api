package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ParticipantContactItemTest {

  private static final String CONTACT_VALUE = "contact";
  private static final String OBSERVATION = "obs";

  private ParticipantContactItem participantContactItem = new ParticipantContactItem();

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContactItem, "contactValue", CONTACT_VALUE);
    Whitebox.setInternalState(participantContactItem, "observation", OBSERVATION);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(CONTACT_VALUE, participantContactItem.getContactValue());
    assertEquals(OBSERVATION, participantContactItem.getObservation());
  }

  @Test
  public void getAllMyAttributes_should_return_HashMap_String_object_instance(){
    assertEquals(CONTACT_VALUE, participantContactItem.getAllMyAttributes().get("contactValue"));
  }

  @Test
  public void getContactValueAttribute_should_return_HashMap_String_object_instance(){
    assertEquals(CONTACT_VALUE, participantContactItem.getContactValueAttribute().get("contactValue"));
  }

  @Test
  public void serialize_static_method_should_convert_objectModel_to_JsonString(){
    assertTrue(ParticipantContactItem.serialize(participantContactItem) instanceof String);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    String participantContactItemJson = ParticipantContactItem.serialize(participantContactItem);
    assertTrue(ParticipantContactItem.deserialize(participantContactItemJson) instanceof ParticipantContactItem);
  }

  @Test
  public void isValid_method_should_return_TRUE(){
    assertTrue(participantContactItem.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_empty_contactValue(){
    Whitebox.setInternalState(participantContactItem, "contactValue", "");
    assertFalse(participantContactItem.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_contactValue(){
    Whitebox.setInternalState(participantContactItem, "contactValue", (String)null);
    assertFalse(participantContactItem.isValid());
  }

}
