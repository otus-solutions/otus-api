package org.ccem.otus.model.participant_contact;

import org.ccem.otus.participant.model.participant_contact.Email;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

public class EmailTest {

  private static final String EMAIL_CONTENT = "user@otus";

  private Email email = PowerMockito.spy(new Email());

  @Before
  public void setUp(){
    Whitebox.setInternalState(email, "content", EMAIL_CONTENT);
  }

  @Test
  public void test_for_invoke_getter(){
    assertEquals(EMAIL_CONTENT, email.getContent());
  }

  @Test
  public void test_setContent_method(){
    final String EMAIL_CONTENT_2 = EMAIL_CONTENT + "2";
    email.setContent(EMAIL_CONTENT_2);
    assertEquals(EMAIL_CONTENT_2, email.getContent());
  }

  @Test
  public void isValid_method_should_return_TRUE(){
    assertTrue(email.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_empty_content(){
    Whitebox.setInternalState(email, "content", "");
    assertFalse(email.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_content(){
    Whitebox.setInternalState(email, "content", (String)null);
    assertFalse(email.isValid());
  }

  @Test
  public void deserialize_method_should_convert_StringJson_to_objectModel(){
    assertTrue(Email.deserialize("{}") instanceof Email);
  }

  @Test
  public void toJson_method_should_convert_objectModel_to_StringJson(){
    Email nonSpyEmail = new Email();
    nonSpyEmail.setContent(EMAIL_CONTENT);
    assertTrue(nonSpyEmail.toJson() instanceof String);
  }

}
