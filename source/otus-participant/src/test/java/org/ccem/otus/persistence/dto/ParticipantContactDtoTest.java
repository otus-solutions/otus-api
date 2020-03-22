package org.ccem.otus.persistence.dto;

import com.google.gson.internal.LinkedTreeMap;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.*;
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
  private static final String POSITION = ParticipantContactPositionOptions.SECOND.getName();
  private static final String TYPE = ParticipantContactTypeOptions.EMAIL.getName();
  private static final String INVALID_STRING_VALUE_FOR_ANY_FIELD = "0";

  @InjectMocks
  private ParticipantContactDto participantContactDto = PowerMockito.spy(new ParticipantContactDto());

  private LinkedTreeMap contactItem = new LinkedTreeMap();

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContactDto, "_id", ID);
    Whitebox.setInternalState(participantContactDto, "position",  POSITION);
    Whitebox.setInternalState(participantContactDto, "contactItem", contactItem);
    Whitebox.setInternalState(participantContactDto, "type", TYPE);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(ID, participantContactDto.getIdStr());
    assertEquals(new ObjectId(ID), participantContactDto.getObjectId());
    assertEquals(POSITION, participantContactDto.getPosition());
    assertEquals(contactItem, participantContactDto.getContactItem());
    assertEquals(TYPE, participantContactDto.getType());
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
  public void isValid_method_should_return_TRUE(){
    assertTrue(participantContactDto.isValid());
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValid_method_should_throw_exception_if_ID_is_null(){
    Whitebox.setInternalState(participantContactDto, "_id", (String)null);
    participantContactDto.isValid();
  }

  @Test
  public void isValid_method_should_return_FALSE_if_ID_is_not_a_hexString(){
    Whitebox.setInternalState(participantContactDto, "_id", INVALID_STRING_VALUE_FOR_ANY_FIELD);
    assertFalse(participantContactDto.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_position(){
    Whitebox.setInternalState(participantContactDto, "position", (String)null);
    assertFalse(participantContactDto.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_invalid_position(){
    Whitebox.setInternalState(participantContactDto, "position", INVALID_STRING_VALUE_FOR_ANY_FIELD);
    assertFalse(participantContactDto.isValid());
  }

  @Test
  public void isValid_method_should_return_TRUE_in_case_null_type(){
    Whitebox.setInternalState(participantContactDto, "type", (String)null);
    assertTrue(participantContactDto.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_invalid_type(){
    Whitebox.setInternalState(participantContactDto, "type", INVALID_STRING_VALUE_FOR_ANY_FIELD);
    assertFalse(participantContactDto.isValid());
  }

}
