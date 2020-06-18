package br.org.otus.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

  @Before
  public void setUp() throws Exception {}

  @Test
  public void should_check_if_there_is_lowerCaseTreatment_for_emailAttribute_by_serializeMethod(){
    User userWithEmail = User.deserialize("{\"email\": \"ABC@GMAIL.COM\"}");
    assertTrue(User.serialize(userWithEmail).contains("\"email\":\"abc@gmail.com\""));
  }

  @Test
  public void should_check_if_there_is_lowerCaseTreatment_for_emailAttribute_by_deserializeMethod(){
    String userJson = "{\"email\": \"ABC@GMAIL.COM\"}";
    assertEquals(User.deserialize(userJson).getEmail(), "abc@gmail.com");
  }
}