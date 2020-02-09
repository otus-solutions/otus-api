package br.org.otus.security.dtos;

import com.google.gson.Gson;
import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class PasswordResetRequestDtoTest {

  private static final String TOKEN = "4651651651651651";
  private static final String EMAIL = "teste@teste.com";
  private static final String URL = "http://url.com.br";

  private PasswordResetRequestDto passwordResetRequestDto;

  @Before
  public void setUp() {
    passwordResetRequestDto = new PasswordResetRequestDto();
    passwordResetRequestDto.setToken(TOKEN);
    passwordResetRequestDto.setUserEmail(EMAIL);
    passwordResetRequestDto.setRedirectUrl(URL);
  }

  @Test
  public void isValid_should_return_positive_when_exist_email_field() {
    assertTrue(passwordResetRequestDto.isValid());
  }

  @Test
  public void isValid_should_return_negative_when_not_exist_email_field() {
    passwordResetRequestDto.setUserEmail(null);
    assertFalse(passwordResetRequestDto.isValid());
  }

  @Test
  public void should_build_correct_claims() throws ParseException {
    JWTClaimsSet jwtClaimsSet = passwordResetRequestDto.buildClaimSet();
    assertEquals(EMAIL, jwtClaimsSet.getIssuer());
    assertEquals("password-reset", jwtClaimsSet.getStringClaim("mode"));
  }

  @Test
  public void should_return_GsonBuilder() {
    assertNotNull(PasswordResetRequestDto.getGsonBuilder());
  }

  @Test
  public void should_return_token() {
    assertEquals(TOKEN, passwordResetRequestDto.getToken());
  }

  @Test
  public void should_return_url() {
    assertEquals(URL, passwordResetRequestDto.getRedirectUrl());
  }

  @Test
  public void should_build_correct_instance_object() {
    String passwordResetRequestDtoToCompareJson = new Gson().toJson(passwordResetRequestDto);
    assertEquals(passwordResetRequestDto, PasswordResetRequestDto.deserialize(passwordResetRequestDtoToCompareJson));
  }

  @Test
  public void should_correct_compare_instances() {
    PasswordResetRequestDto passwordResetRequestDtoToCompare = new PasswordResetRequestDto();
    passwordResetRequestDtoToCompare.setUserEmail(EMAIL);
    passwordResetRequestDtoToCompare.setRedirectUrl(URL);
    passwordResetRequestDtoToCompare.setToken(TOKEN);

    assertTrue(passwordResetRequestDtoToCompare.equals(passwordResetRequestDto));
  }
}