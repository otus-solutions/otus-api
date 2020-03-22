package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.*;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@PrepareForTest({ParticipantContactItem.class})
public class ParticipantContactItemSetTest {

  private ParticipantContactItemSet participantContactItemSet = new ParticipantContactItemSet<Email>();
  private ParticipantContactItem mainItemValue = PowerMockito.spy(new ParticipantContactItem<Email>());
  private ParticipantContactItem secondItemValue = PowerMockito.spy(new ParticipantContactItem<Email>());
  private ParticipantContactItem thirdItemValue = PowerMockito.spy(new ParticipantContactItem<Email>());
  private ParticipantContactItem fourthItemValue = PowerMockito.spy(new ParticipantContactItem<Email>());
  private ParticipantContactItem fifthItemValue = PowerMockito.spy(new ParticipantContactItem<Email>());

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContactItemSet, "main", mainItemValue);
    Whitebox.setInternalState(participantContactItemSet, "second", secondItemValue);
    Whitebox.setInternalState(participantContactItemSet, "third", thirdItemValue);
    Whitebox.setInternalState(participantContactItemSet, "fourth", fourthItemValue);
    Whitebox.setInternalState(participantContactItemSet, "fifth", fifthItemValue);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(mainItemValue, participantContactItemSet.getMain());
    assertEquals(secondItemValue, participantContactItemSet.getSecond());
    assertEquals(thirdItemValue, participantContactItemSet.getThird());
    assertEquals(fourthItemValue, participantContactItemSet.getFourth());
    assertEquals(fifthItemValue, participantContactItemSet.getFifth());
  }

  @Test
  public void getItemByPosition_method_should_return_main_itemSet(){
    assertEquals(mainItemValue, participantContactItemSet.getItemByPosition(ParticipantContactPositionOptions.MAIN));
  }

  @Test
  public void getPositionOfLastItem_method_should_return_FIFTH_itemSet_not_null(){
    assertEquals(ParticipantContactPositionOptions.FIFTH, participantContactItemSet.getPositionOfLastItem());
  }

  @Test
  public void getPositionOfLastItem_method_should_return_FORTH_itemSet(){
    Whitebox.setInternalState(participantContactItemSet, "fifth", (ParticipantContactItem<Email>)null);
    assertEquals(ParticipantContactPositionOptions.FOURTH, participantContactItemSet.getPositionOfLastItem());
  }

  @Test
  public void getPositionOfLastItem_method_should_return_THIRD_itemSet(){
    Whitebox.setInternalState(participantContactItemSet, "fifth", (ParticipantContactItem<Email>)null);
    Whitebox.setInternalState(participantContactItemSet, "fourth", (ParticipantContactItem<Email>)null);
    assertEquals(ParticipantContactPositionOptions.THIRD, participantContactItemSet.getPositionOfLastItem());
  }

  @Test
  public void getPositionOfLastItem_method_should_return_SECOND_itemSet(){
    Whitebox.setInternalState(participantContactItemSet, "fifth", (ParticipantContactItem<Email>)null);
    Whitebox.setInternalState(participantContactItemSet, "fourth", (ParticipantContactItem<Email>)null);
    Whitebox.setInternalState(participantContactItemSet, "third", (ParticipantContactItem<Email>)null);
    assertEquals(ParticipantContactPositionOptions.SECOND, participantContactItemSet.getPositionOfLastItem());
  }

  @Test
  public void getPositionOfLastItem_method_should_return_MAIN_itemSet(){
    Whitebox.setInternalState(participantContactItemSet, "fifth", (ParticipantContactItem<Email>)null);
    Whitebox.setInternalState(participantContactItemSet, "fourth", (ParticipantContactItem<Email>)null);
    Whitebox.setInternalState(participantContactItemSet, "third", (ParticipantContactItem<Email>)null);
    Whitebox.setInternalState(participantContactItemSet, "second", (ParticipantContactItem<Email>)null);
    assertEquals(ParticipantContactPositionOptions.MAIN, participantContactItemSet.getPositionOfLastItem());
  }

  @Test
  public void serialize_static_method_should_convert_objectModel_to_JsonString(){
    assertTrue(ParticipantContactItemSet.serialize(participantContactItemSet) instanceof String);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    String participantContactItemSetJson = ParticipantContactItemSet.serialize(participantContactItemSet);
    assertTrue(ParticipantContactItemSet.deserialize(participantContactItemSetJson) instanceof ParticipantContactItemSet);
  }

}
