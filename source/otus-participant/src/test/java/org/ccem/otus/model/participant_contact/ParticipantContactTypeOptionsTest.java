package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.ParticipantContactTypeOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class ParticipantContactTypeOptionsTest {

  private static final String INVALID_STRING_VALUE = "0";

  @Test
  public void contains_static_method_should_return_TRUE_for_each_enum_value(){
    assertTrue(ParticipantContactTypeOptions.contains(ParticipantContactTypeOptions.EMAIL.getName()));
    assertTrue(ParticipantContactTypeOptions.contains(ParticipantContactTypeOptions.ADDRESS.getName()));
    assertTrue(ParticipantContactTypeOptions.contains(ParticipantContactTypeOptions.PHONE.getName()));
  }

  @Test
  public void contains_static_method_should_return_FALSE_for_invalid_value(){
    assertFalse(ParticipantContactTypeOptions.contains(INVALID_STRING_VALUE));
  }

  @Test
  public void fromString_static_method_should_convert_valid_string_value_to_enum(){
    assertEquals(ParticipantContactTypeOptions.EMAIL, ParticipantContactTypeOptions.fromString(ParticipantContactTypeOptions.EMAIL.getName()));
    assertEquals(ParticipantContactTypeOptions.ADDRESS, ParticipantContactTypeOptions.fromString(ParticipantContactTypeOptions.ADDRESS.getName()));
    assertEquals(ParticipantContactTypeOptions.PHONE, ParticipantContactTypeOptions.fromString(ParticipantContactTypeOptions.PHONE.getName()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromString_static_method_throw_IllegalArgumentException_in_case_invalid_string_value(){
    ParticipantContactTypeOptions.fromString(INVALID_STRING_VALUE);
  }
}
