package br.org.otus.user.dto;

import br.org.otus.security.EncryptorResources;
import com.google.gson.Gson;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordResetDtoTest {
  public static String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoicGFzc3dvcmQtcmVzZXQiLCJpc3MiOiJkaW9nby5yb3Nhcy5mZXJyZWlyYUBnbWFpbC5jb20ifQ.N9ez5TAh3gN-u36zqjwnl58W3RWX6ljBjJ-Kejnn__o";
  public static String EMAIL = "teste@teste.com.br";
  public static String PASSWORD = "Teste123";

  public PasswordResetDto passwordResetDto;
  public String passwordResetDtoJson;

  @Before
  public void setUp() throws EncryptedException {
    passwordResetDto = new PasswordResetDto();
    passwordResetDto.setEmail(EMAIL);
    passwordResetDto.setToken(TOKEN);
    passwordResetDto.setPassword(PASSWORD);
    passwordResetDto.encrypt();

    passwordResetDtoJson = new Gson().toJson(passwordResetDto);
  }

  @Test
  public void shouldDeserializeStringJson() {
    assertEquals(passwordResetDto, PasswordResetDto.deserialize(passwordResetDtoJson));
  }

  @Test
  public void mustBeValidWhenFieldsAreNotEmpty() throws EncryptedException {
    assertTrue(passwordResetDto.isValid());
  }

  @Test
  public void mustNotBeValidWhenFieldsAreNotEmpty() {
    passwordResetDto.setToken(null);
    assertFalse(passwordResetDto.isValid());
  }

  @Test
  public void shouldReturnCorrectToken() {
    assertEquals(passwordResetDto.getToken(), TOKEN);
  }

  @Test
  public void shouldReturnCorrectEncryptedPassword() throws EncryptedException {
    assertEquals(passwordResetDto.getPassword(), EncryptorResources.encryptIrreversible(PASSWORD));
  }

  @Test
  public void shouldReturnCorrectEmail() {
    assertEquals(passwordResetDto.getEmail(), EMAIL);
  }

  @Test
  public void shouldValidEqualsFields() throws EncryptedException {
    PasswordResetDto passwordResetDtoToCompare = new PasswordResetDto();
    passwordResetDtoToCompare.setToken(TOKEN);
    passwordResetDtoToCompare.setPassword(PASSWORD);
    passwordResetDtoToCompare.setEmail(EMAIL);
    passwordResetDtoToCompare.encrypt();

    assertTrue(passwordResetDto.equals(passwordResetDtoToCompare));
  }

  @Test
  public void shouldNotValidEqualToken() throws EncryptedException {
    PasswordResetDto passwordResetDtoToCompare = new PasswordResetDto();
    passwordResetDtoToCompare.setToken(TOKEN + "123");
    passwordResetDtoToCompare.setPassword(PASSWORD);
    passwordResetDtoToCompare.setEmail(EMAIL);
    passwordResetDtoToCompare.encrypt();

    assertFalse(passwordResetDto.equals(passwordResetDtoToCompare));
  }

  @Test
  public void shouldNotValidEqualPassword() throws EncryptedException {
    PasswordResetDto passwordResetDtoToCompare = new PasswordResetDto();
    passwordResetDtoToCompare.setToken(TOKEN);
    passwordResetDtoToCompare.setPassword(PASSWORD + "error");
    passwordResetDtoToCompare.setEmail(EMAIL);
    passwordResetDtoToCompare.encrypt();

    assertFalse(passwordResetDto.equals(passwordResetDtoToCompare));
  }

  @Test
  public void shouldNotValidEqualEmail() throws EncryptedException {
    PasswordResetDto passwordResetDtoToCompare = new PasswordResetDto();
    passwordResetDtoToCompare.setToken(TOKEN);
    passwordResetDtoToCompare.setPassword(PASSWORD);
    passwordResetDtoToCompare.setEmail(EMAIL + ".br");
    passwordResetDtoToCompare.encrypt();

    assertFalse(passwordResetDto.equals(passwordResetDtoToCompare));
  }

  @Test
  public void shouldHasCodeValidCompare() throws EncryptedException {
    PasswordResetDto passwordResetDtoToCompare = new PasswordResetDto();
    passwordResetDtoToCompare.setToken(TOKEN);
    passwordResetDtoToCompare.setPassword(PASSWORD);
    passwordResetDtoToCompare.setEmail(EMAIL);
    passwordResetDtoToCompare.encrypt();

    assertTrue(passwordResetDtoToCompare.equals(passwordResetDto) && passwordResetDto.equals(passwordResetDtoToCompare));
    assertTrue(passwordResetDtoToCompare.hashCode() == passwordResetDto.hashCode());
  }
}