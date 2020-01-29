package br.org.otus.security.dtos;

import br.org.otus.security.EncryptorResources;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticationDtoTest {

  private static final String EMAIL = "teste@teste.com";
  private static final String ADDRESS = "143.54.220.56";
  private static final String PASSWORD = "password";

  private AuthenticationDto authenticationDto;

  @Before
  public void setUp() throws EncryptedException {
    authenticationDto = new AuthenticationDto();
    authenticationDto.setEmail(EMAIL);
    ;
    authenticationDto.setRequestAddress(ADDRESS);
    authenticationDto.password = PASSWORD;

    authenticationDto.encrypt();
  }

  @Test
  public void mustBeValidWhenAllFieldsAreFilled() {
    assertTrue(authenticationDto.isValid());
  }

  @Test
  public void mustBeInvalidWhenSomeFieldsAreNotFilled() {
    authenticationDto.userEmail = null;
    assertFalse(authenticationDto.isValid());
  }

  @Test
  public void shouldEncriptThePassword() throws EncryptedException {
    assertEquals(authenticationDto.password, EncryptorResources.encryptIrreversible(PASSWORD));
  }

  @Test
  public void shouldReturnEmail() {
    assertEquals(authenticationDto.getUserEmail(), EMAIL);
  }

  @Test
  public void shouldReturnKey() throws EncryptedException {
    assertEquals(authenticationDto.getKey(), EncryptorResources.encryptIrreversible(PASSWORD));
  }

  @Test
  public void shouldReturnAddress() {
    assertEquals(authenticationDto.getRequestAddress(), ADDRESS);
  }

  @Test
  public void shouldReturnMode() {
    assertEquals(authenticationDto.getMode(), "user");
  }

  @Test
  public void shouldFillClaimsSetWithCorretValues() {
    JWTClaimsSet jwtClaimsSet = authenticationDto.buildClaimSet();
    assertEquals(jwtClaimsSet.getClaim("mode"), "user");
    assertEquals(jwtClaimsSet.getIssuer(), EMAIL);
  }
}