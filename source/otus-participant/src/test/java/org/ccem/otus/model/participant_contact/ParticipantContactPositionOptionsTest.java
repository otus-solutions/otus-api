package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.ParticipantContactPositionOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantContactPositionOptionsTest {

  private static final String INVALID_STRING_VALUE = "x";
  private static final int INVALID_INT_RANKING = -1;

  @Test
  public void contains_static_method_should_return_TRUE_for_each_enum_value(){
    assertTrue(ParticipantContactPositionOptions.contains(ParticipantContactPositionOptions.MAIN.getName()));
    assertTrue(ParticipantContactPositionOptions.contains(ParticipantContactPositionOptions.SECOND.getName()));
    assertTrue(ParticipantContactPositionOptions.contains(ParticipantContactPositionOptions.THIRD.getName()));
    assertTrue(ParticipantContactPositionOptions.contains(ParticipantContactPositionOptions.FOURTH.getName()));
    assertTrue(ParticipantContactPositionOptions.contains(ParticipantContactPositionOptions.FIFTH.getName()));
  }

  @Test
  public void contains_static_method_should_return_FALSE_for_invalid_value(){
    assertFalse(ParticipantContactPositionOptions.contains(INVALID_STRING_VALUE));
  }

  @Test
  public void fromString_static_method_should_convert_valid_string_value_to_enum(){
    assertEquals(ParticipantContactPositionOptions.MAIN, ParticipantContactPositionOptions.fromString(ParticipantContactPositionOptions.MAIN.getName()));
    assertEquals(ParticipantContactPositionOptions.SECOND, ParticipantContactPositionOptions.fromString(ParticipantContactPositionOptions.SECOND.getName()));
    assertEquals(ParticipantContactPositionOptions.THIRD, ParticipantContactPositionOptions.fromString(ParticipantContactPositionOptions.THIRD.getName()));
    assertEquals(ParticipantContactPositionOptions.FOURTH, ParticipantContactPositionOptions.fromString(ParticipantContactPositionOptions.FOURTH.getName()));
    assertEquals(ParticipantContactPositionOptions.FIFTH, ParticipantContactPositionOptions.fromString(ParticipantContactPositionOptions.FIFTH.getName()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromString_static_method_throw_IllegalArgumentException_in_case_invalid_string_value(){
    ParticipantContactPositionOptions.fromString(INVALID_STRING_VALUE);
  }

  @Test
  public void fromInt_static_method_should_convert_valid_string_value_to_enum(){
    assertEquals(ParticipantContactPositionOptions.MAIN, ParticipantContactPositionOptions.fromInt(ParticipantContactPositionOptions.MAIN.getRanking()));
    assertEquals(ParticipantContactPositionOptions.SECOND, ParticipantContactPositionOptions.fromInt(ParticipantContactPositionOptions.SECOND.getRanking()));
    assertEquals(ParticipantContactPositionOptions.THIRD, ParticipantContactPositionOptions.fromInt(ParticipantContactPositionOptions.THIRD.getRanking()));
    assertEquals(ParticipantContactPositionOptions.FOURTH, ParticipantContactPositionOptions.fromInt(ParticipantContactPositionOptions.FOURTH.getRanking()));
    assertEquals(ParticipantContactPositionOptions.FIFTH, ParticipantContactPositionOptions.fromInt(ParticipantContactPositionOptions.FIFTH.getRanking()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromInt_static_method_throw_IllegalArgumentException_in_case_invalid_string_value(){
    ParticipantContactPositionOptions.fromInt(INVALID_INT_RANKING);
  }
}
