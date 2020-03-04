package org.ccem.otus.persistence.dto;

import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactTypeOptions;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantContactDtoTest {

  private static final String ID = "5e13997795818e14a91a5268";
  private static final String TYPE = ParticipantContactTypeOptions.EMAIL.getName();
  private static final String INVALID_STRING_VALUE_FOR_FIELD = "abc";
  private static final ParticipantContactItem participantContactItem = new ParticipantContactItem();
  private static final Integer secondaryContactIndex = 0;

  private ParticipantContactDto participantContactDto = new ParticipantContactDto();

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContactDto, "_id", ID);
    Whitebox.setInternalState(participantContactDto, "type", TYPE);
    Whitebox.setInternalState(participantContactDto, "participantContactItem", participantContactItem);
    Whitebox.setInternalState(participantContactDto, "secondaryContactIndex", secondaryContactIndex);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(ID, participantContactDto.getIdStr());
    assertEquals(new ObjectId(ID), participantContactDto.getObjectId());
    assertEquals(TYPE, participantContactDto.getType());
    assertEquals(participantContactItem, participantContactDto.getParticipantContactItem());
    assertEquals(secondaryContactIndex, participantContactDto.getSecondaryContactIndex());
  }

  @Test
  public void serialize_static_method_should_convert_objectModel_to_JsonString(){
    assertTrue(ParticipantContactDto.serialize(participantContactDto) instanceof String);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    String participantContactDtoJson = ParticipantContactDto.serialize(participantContactDto);
    assertTrue(ParticipantContactDto.deserialize(participantContactDtoJson) instanceof ParticipantContactDto);
  }

  @Test
  public void isValid_method_should_return_TRUE(){
    //PowerMockito.when()
    assertTrue(participantContactDto.isValid());
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValid_method_should_throw_exception_if_ID_is_not_a_ObjectId_String(){
    Whitebox.setInternalState(participantContactDto, "_id", INVALID_STRING_VALUE_FOR_FIELD);
    participantContactDto.isValid();
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_invalid_type(){
    Whitebox.setInternalState(participantContactDto, "type", INVALID_STRING_VALUE_FOR_FIELD);
    assertFalse(participantContactDto.isValid());
  }

  @Test
  public void isValid_method_should_return_TRUE_in_case_null_participantContactItem(){
    Whitebox.setInternalState(participantContactDto, "participantContactItem", (ParticipantContactItem)null);
    assertTrue(participantContactDto.isValid());
  }

  //@Test
  public void isValid_method_should_return_FALSE_in_case_participantContactItem_invalid(){
    Whitebox.setInternalState(participantContactDto, "participantContactItem", (ParticipantContactItem)null);
    assertFalse(participantContactDto.isValid());
  }

}
