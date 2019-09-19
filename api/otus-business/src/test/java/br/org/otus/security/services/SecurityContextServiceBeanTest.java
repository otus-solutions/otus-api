package br.org.otus.security.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Set;

import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(SecurityContextServiceBean.class)
@PowerMockIgnore("javax.crypto.*")

public class SecurityContextServiceBeanTest {
	private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	private static final String MODE = "client";
	private static final String USER = "Otus Local";
	private static final String SERIALIZE_EXPECTED = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoiY2xpZW50IiwiaXNzIjoiT3R1cyBMb2NhbCJ9.2IySh22tfoVuCefGFxhGJgR6Q83wpJUWW4Efv376XKk";
	private static final Boolean POSITIVE_ANSWER = true;
	private static final Object RETURN_NULL = null;

	@InjectMocks
	private SecurityContextServiceBean securityContextServiceBean;
	@Mock
	private SecurityContext securityContext;
	@Mock
	private AuthenticationData authenticationData;
	@Mock
	private SecureRandom secureRandom;
	private SessionIdentifier sessionIdentifier;
	private SignedJWT signedJWT;
	private byte[] secretKey;
	private MACSigner signer;
	private JWSHeader jwsHeader;
	private byte[] sharedSecret;
	

	@Before
	public void setUp() throws Exception {
		secretKey = TOKEN.getBytes();
		sessionIdentifier = spy(new SessionIdentifier(TOKEN, secretKey, authenticationData));
		signer = new MACSigner(secretKey);
		whenNew(MACSigner.class).withArguments(secretKey).thenReturn(signer);

		JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
		builder.issuer(USER);
		builder.claim("mode", MODE);
		JWTClaimsSet buildClaim = builder.build();
		when(authenticationData.buildClaimSet()).thenReturn(buildClaim);

		jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
		signedJWT = spy(new SignedJWT(jwsHeader, buildClaim));
		whenNew(SignedJWT.class).withAnyArguments().thenReturn(signedJWT);

	}

	@Test
	public void method_generateToken_should_return_serialize_token() throws Exception {
		assertEquals(SERIALIZE_EXPECTED, securityContextServiceBean.generateToken(authenticationData, secretKey));
	}

	@Test(expected = TokenException.class)
	@Ignore
	public void method_generateToken_should_throws_JOSEException() throws Exception {
		doThrow(new JOSEException(null)).when(signedJWT, "sign", signer);
		securityContextServiceBean.generateToken(authenticationData, secretKey);
	}

	@Test
	public void method_generateSecretKey_should_return_randomSharedSecret() throws Exception {
		assertTrue(securityContextServiceBean.generateSecretKey() instanceof byte[]);
	}

	@Test
	public void method_addSession_should_evocate_addSession_of_securityContext() {
		securityContextServiceBean.addSession(sessionIdentifier);
		verify(securityContext).addSession(sessionIdentifier);

	}

	@Test
	public void method_removeToken_should_evocate_removeSession_of_securityContext() {
		securityContextServiceBean.removeToken(TOKEN);
		verify(securityContext).removeSession(TOKEN);
	}

	@Test
	public void method_validateToken_should_evocate_verifySignature_of_securityContext()
			throws TokenException, ParseException, JOSEException {
		when(securityContext.hasToken(TOKEN)).thenReturn(POSITIVE_ANSWER);
		securityContextServiceBean.validateToken(TOKEN);
		verify(securityContext).verifySignature(TOKEN);
	}

	@Test(expected = TokenException.class)
	public void method_validateToken_should_throw_TokenException()
			throws TokenException, ParseException, JOSEException {
		securityContextServiceBean.validateToken(TOKEN);
	}

	@Test(expected = TokenException.class)
	public void method_validateToken_should_throw_ParseException()
			throws TokenException, ParseException, JOSEException {
		when(securityContext.hasToken(TOKEN)).thenReturn(POSITIVE_ANSWER);
		when(securityContext.verifySignature(TOKEN)).thenThrow(ParseException.class);
		securityContextServiceBean.validateToken(TOKEN);
	}

	@Test(expected = TokenException.class)
	public void method_validateToken_should_throw_JOSEException() throws TokenException, ParseException, JOSEException {
		when(securityContext.hasToken(TOKEN)).thenReturn(POSITIVE_ANSWER);
		when(securityContext.verifySignature(TOKEN)).thenThrow(JOSEException.class);
		securityContextServiceBean.validateToken(TOKEN);
	}

	@Test
	public void method_getSession_should_return_SessionIdentifier() {
		when(securityContext.getSession(TOKEN)).thenReturn(sessionIdentifier);
		assertEquals(sessionIdentifier.getToken(), securityContextServiceBean.getSession(TOKEN).getToken());
		assertEquals(sessionIdentifier, securityContext.getSession(TOKEN));
	}

	@Test
	public void method_getSession_should_return_value_null() {
		when(securityContext.getSession(TOKEN)).thenThrow(NoSuchElementException.class);
		assertEquals(RETURN_NULL, securityContextServiceBean.getSession(TOKEN));
	}

}
