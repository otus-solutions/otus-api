package br.org.otus.user.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.security.EncryptorResources;

public class SignupDataDtoTest {
	private static final String NAME = "Otus";
	private static final String SURNAME = "Api";
	private static final String PHONE = "888-765432";
	private static final String EMAIL = "otus@otus.com";
	private static final String PASSWORD = "#12345";
	private static final String PASSWORD_CONFIRMATION = "#12345";
	private static final String PASSWORD_ENCRYTED = "soLP/dLXB3RRT5buAtuEs0DWXxk=";

	private SignupDataDto signupDataDto;

	@Before
	public void setUp() {
	  signupDataDto = new SignupDataDto();
		signupDataDto.setName(NAME);
		signupDataDto.setSurname(SURNAME);
		signupDataDto.setPhone(PHONE);
		signupDataDto.setEmail(EMAIL);
		signupDataDto.setPassword(PASSWORD);
		signupDataDto.setPasswordConfirmation(PASSWORD_CONFIRMATION);
	}

	@Test
	public void method_isValid_should_return_positive_answer_if_atributes_not_empty() throws Exception {
		assertTrue(signupDataDto.isValid());
	}

  @Test
  public void method_isValid_should_return_negative_answer_if_atributes_empty() throws Exception {
	  signupDataDto.setEmail(null);
    assertFalse(signupDataDto.isValid());
  }

	@Test
	public void method_encrypt_should_encryp_passwords_atributes() throws Exception {
		signupDataDto.encrypt();
		assertEquals(PASSWORD_ENCRYTED, signupDataDto.getPassword());
		assertEquals(PASSWORD_ENCRYTED, signupDataDto.getPasswordConfirmation());
	}

	@Test
	public void should_return_name(){
    assertEquals(NAME, signupDataDto.getName());
  }

  @Test
  public void should_return_surname(){
    assertEquals(SURNAME, signupDataDto.getSurname());
  }

  @Test
  public void should_return_phone(){
    assertEquals(PHONE, signupDataDto.getPhone());
  }

  @Test
  public void should_return_email(){
    assertEquals(EMAIL, signupDataDto.getEmail());
  }
}
