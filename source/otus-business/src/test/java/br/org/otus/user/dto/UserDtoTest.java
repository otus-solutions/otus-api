package br.org.otus.user.dto;

import br.org.otus.security.EncryptorResources;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;

import static br.org.otus.user.dto.PasswordResetDtoTest.EMAIL;
import static org.junit.Assert.*;

public class UserDtoTest {

  private static final String PASSWORD_CONFIRMATION = "teste123";
  private static final String NAME = "User";
  private static final String SURNAME = "Surname";
  private static final String PHONE = "5555-5555-5555";
  private static final String PASSWORD = "teste123";

  private UserDto userDto;

  @Before
  public void setUp() {
    userDto = new UserDto();
    userDto.setName(NAME);
    userDto.setSurname(SURNAME);
    userDto.setPhone(PHONE);
    userDto.setEmail(EMAIL);
    userDto.setPassword(PASSWORD);
    userDto.setPasswordConfirmation(PASSWORD_CONFIRMATION);
  }

  @Test
  public void should_encrypt_passwords() throws EncryptedException {
    userDto.encrypt();
    assertEquals(EncryptorResources.encryptIrreversible(PASSWORD), userDto.getPassword());
    assertEquals(EncryptorResources.encryptIrreversible(PASSWORD_CONFIRMATION), userDto.getPasswordConfirmation());
  }

  @Test
  public void should_return_name() {
    assertEquals(NAME, userDto.getName());
  }

  @Test
  public void should_return_surname() {
    assertEquals(SURNAME, userDto.getSurname());
  }

  @Test
  public void should_return_phone() {
    assertEquals(PHONE, userDto.getPhone());
  }

  @Test
  public void should_return_email() {
    assertEquals(EMAIL, userDto.getEmail());
  }
}