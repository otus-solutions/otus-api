package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.PhoneNumber;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

public class PhoneNumberTest {

  private static final String PHONE_NUMBER_CONTENT = "51987654321";

  private PhoneNumber phoneNumber = PowerMockito.spy(new PhoneNumber());

  @Before
  public void setUp(){
    Whitebox.setInternalState(phoneNumber, "content", PHONE_NUMBER_CONTENT);
  }

  @Test
  public void test_for_invoke_getter(){
    assertEquals(PHONE_NUMBER_CONTENT, phoneNumber.getContent());
  }

  @Test
  public void test_setContent_method(){
    final String EMAIL_CONTENT_2 = PHONE_NUMBER_CONTENT + "2";
    phoneNumber.setContent(EMAIL_CONTENT_2);
    assertEquals(EMAIL_CONTENT_2, phoneNumber.getContent());
  }

  @Test
  public void isValid_method_should_return_TRUE(){
    assertTrue(phoneNumber.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_empty_content(){
    Whitebox.setInternalState(phoneNumber, "content", "");
    assertFalse(phoneNumber.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_content(){
    Whitebox.setInternalState(phoneNumber, "content", (String)null);
    assertFalse(phoneNumber.isValid());
  }

  @Test
  public void deserialize_method_should_convert_StringJson_to_objectModel(){
    assertTrue(PhoneNumber.deserialize("{}") instanceof PhoneNumber);
  }

  @Test
  public void toJson_method_should_convert_objectModel_to_StringJson(){
    PhoneNumber nonSpyPhoneNumber = new PhoneNumber();
    nonSpyPhoneNumber.setContent(PHONE_NUMBER_CONTENT);
    assertTrue(nonSpyPhoneNumber.toJson() instanceof String);
  }

}
