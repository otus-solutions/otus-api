package org.ccem.otus.persistence.dto;

import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactTypeOptions;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import static org.powermock.api.mockito.PowerMockito.when;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantContactDtoTest {

  private static final String ID = "5e13997795818e14a91a5268";
  private static final String TYPE = ParticipantContactTypeOptions.EMAIL.getName();
  private static final String INVALID_STRING_VALUE_FOR_FIELD = "abc";
  private static final Integer SECONDARY_CONTACT_INDEX = 0;
  private static final Integer INVALID_SECONDARY_CONTACT_INDEX = -1;

  @InjectMocks
  private ParticipantContactDto participantContactDto = PowerMockito.spy(new ParticipantContactDto());
  @Mock
  private ParticipantContactItem participantContactItem = PowerMockito.spy(new ParticipantContactItem());

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContactDto, "_id", ID);
    Whitebox.setInternalState(participantContactDto, "type", TYPE);
    Whitebox.setInternalState(participantContactDto, "participantContactItem", participantContactItem);
    Whitebox.setInternalState(participantContactDto, "secondaryContactIndex", SECONDARY_CONTACT_INDEX);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(ID, participantContactDto.getIdStr());
    assertEquals(new ObjectId(ID), participantContactDto.getObjectId());
    assertEquals(TYPE, participantContactDto.getType());
    assertEquals(participantContactItem, participantContactDto.getParticipantContactItem());
    assertEquals(SECONDARY_CONTACT_INDEX, participantContactDto.getSecondaryContactIndex());
  }

  @Test
  public void serialize_static_method_should_convert_objectModel_to_JsonString(){
    assertTrue(ParticipantContactDto.serialize(new ParticipantContactDto()) instanceof String);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    assertTrue(ParticipantContactDto.deserialize("{}") instanceof ParticipantContactDto);
  }

  @Test
  public void isValid_method_should_return_TRUE_in_case_valid_attributes() throws Exception {
    when(participantContactItem, "isValid").thenReturn(true);
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

  @Test
  public void isValid_method_should_return_FALSE_in_case_invalid_participantContactItem(){
    assertFalse(participantContactDto.isValid());
  }

  @Test
  public void isValid_method_should_return_TRUE_in_case_null_secondaryContactIndex() throws Exception {
    when(participantContactItem, "isValid").thenReturn(true);
    Whitebox.setInternalState(participantContactDto, "secondaryContactIndex", (Integer)null);
    assertTrue(participantContactDto.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_invalid_secondaryContactIndex() throws Exception {
    when(participantContactItem, "isValid").thenReturn(true);
    Whitebox.setInternalState(participantContactDto, "secondaryContactIndex", INVALID_SECONDARY_CONTACT_INDEX);
    assertFalse(participantContactDto.isValid());
  }
}
