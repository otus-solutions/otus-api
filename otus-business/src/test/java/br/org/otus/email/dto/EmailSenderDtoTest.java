package br.org.otus.email.dto;

import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderDtoTest {
    private static String EMAIL = "otus@ous.com";
    private static String NAME = "dumy";
    private static String PASSWORD = "teste123";
    private static String ENCRYPTED_PASSWORD = "dGVzdGUxMjM=";
    private static String PASSWORD_CONFIRMATION = "teste";
    private static String ENCRYPTED_PASSWORD_CONFIRMATION = "dGVzdGU=";

    @InjectMocks
    private EmailSenderDto emailSenderDto;

    @Before
    public void setUp(){
        emailSenderDto.setEmail(EMAIL);
        emailSenderDto.setName(NAME);
        emailSenderDto.setPassword(PASSWORD);
        emailSenderDto.setPasswordConfirmation(PASSWORD_CONFIRMATION);
    }

    @Test
    public void getName_method_should_return_NAME(){
        assertEquals(NAME,emailSenderDto.getName());
    }

    @Test
    public void getEmaile_method_should_return_EMAIL(){
        assertEquals(EMAIL,emailSenderDto.getEmail());
    }

    @Test
    public void getPassword_method_should_return_PASSWORD(){
        assertEquals(PASSWORD,emailSenderDto.getPassword());
    }

    @Test
    public void getPasswordConfirmation_method_should_return_PASSWORD_CONFIRMATION(){
        assertEquals(PASSWORD_CONFIRMATION,emailSenderDto.getPasswordConfirmation());
    }

    @Test
    public void encrypt_method_should_encrypt_PASSWORD_and_PASSWORD_CONFIRMATION() throws EncryptedException {
        emailSenderDto.encrypt();
        assertEquals(ENCRYPTED_PASSWORD,emailSenderDto.getPassword());
        assertEquals(ENCRYPTED_PASSWORD_CONFIRMATION,emailSenderDto.getPasswordConfirmation());
    }
}
