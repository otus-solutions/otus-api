package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.ParticipantContactTypeOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ParticipantContactTypeOptionsTest {

  @Test
  public void contains_static_method_should_return_TRUE_for_each_enum_value(){
    assertTrue(ParticipantContactTypeOptions.contains(ParticipantContactTypeOptions.EMAIL.getName()));
    assertTrue(ParticipantContactTypeOptions.contains(ParticipantContactTypeOptions.ADDRESS.getName()));
    assertTrue(ParticipantContactTypeOptions.contains(ParticipantContactTypeOptions.PHONE.getName()));
  }

  @Test
  public void contains_static_method_should_return_FALSE_for_invalid_value(){
    assertFalse(ParticipantContactTypeOptions.contains(""));
  }
}
