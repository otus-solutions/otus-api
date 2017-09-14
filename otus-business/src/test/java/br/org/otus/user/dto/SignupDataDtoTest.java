package br.org.otus.user.dto;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.security.EncryptorResources;
import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SignupDataDto.class, EncryptorResources.class})
public class SignupDataDtoTest {
	private static final String NAME = "Otus";
	private static final String SURNAME = "Api";
	private static final String PHONE = "888-765432";
	private static final String EMAIL = "otus@otus.com";
	private static final String PASSWORD = "#12345";
	private static final String PASSWORD_CONFIRMATION = "#12345";
	private static final String PASSWORD_ENCRYTED = "soLP/dLXB3RRT5buAtuEs0DWXxk=";
	
	private SignupDataDto signupDataDto = PowerMockito.spy(new SignupDataDto());
	

	@Before
	public void setUp() {
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
		PowerMockito.verifyPrivate(signupDataDto).invoke("isPasswordConfirmed");
	
	}

	@Test
	public void method_encrypt() throws Exception {
		mockStatic(EncryptorResources.class);
		when(EncryptorResources.class, "encryptIrreversible", Mockito.anyString()).thenReturn(PASSWORD_ENCRYTED);		
		signupDataDto.encrypt();
		assertEquals(PASSWORD_ENCRYTED, signupDataDto.getPassword());
		assertEquals(PASSWORD_ENCRYTED, signupDataDto.getPasswordConfirmation());
		
				

	}
	//Whitebox.invokeMethod(participantImportServiceBean, "performImportation", participantImports);

}
