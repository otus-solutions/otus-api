package br.org.otus.security.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SecurityContext.class, SignedJWT.class })
public class SecurityContextTest {
	private static final Boolean POSITIVE_ANSWER = true;
	private static final Boolean NEGATIVE_ANSWER = false;
	private static final String TOKEN_BEARER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";

	@InjectMocks
	private SecurityContext securityContext;
	@Mock
	private SessionIdentifier sessionIdentifier;
	@Mock
	private Stream<SessionIdentifier> sessionIndentifierStream;
	@Mock
	private Optional<SessionIdentifier> sessionIdentifierOptional;
	@Mock
	private MACVerifier verifier;
	private HashSet<SessionIdentifier> sessions;
	private byte[] sharedSecret;
	private SignedJWT signedJWT;

	@Before
	public void setUp() throws Exception {
		sessions = Mockito.spy(new HashSet<>());
		whenNew(HashSet.class).withNoArguments().thenReturn(sessions);
		securityContext.setUp();
		when(sessions.stream()).thenReturn(sessionIndentifierStream);
	}

	@Test
	public void method_addSession_should_evocate_add_of_sessions() {
		when(sessions.add(sessionIdentifier)).thenReturn(POSITIVE_ANSWER);
		securityContext.addSession(sessionIdentifier);
		verify(sessions).add(sessionIdentifier);
	}

	@Test
	public void method_removeSession_should_evocate_removeIf_of_sessions() {
		securityContext.removeSession(TOKEN_BEARER);
		verify(sessions).removeIf(any());
	}

	@Test
	public void method_getSession() {
		when(sessionIndentifierStream.filter(any())).thenReturn(sessionIndentifierStream);
		when(sessionIndentifierStream.findFirst()).thenReturn(sessionIdentifierOptional);
		when(sessionIdentifierOptional.get()).thenReturn(sessionIdentifier);
		assertTrue(securityContext.getSession(TOKEN) instanceof SessionIdentifier);
	}

	@Test
	public void method_hasToken_should_return_positive_answer() {
		when(sessionIndentifierStream.anyMatch(any())).thenReturn(POSITIVE_ANSWER);
		assertTrue(securityContext.hasToken(TOKEN));
	}

	@Test
	public void method_hasToken_should_return_negative_answer() {
		when(sessionIndentifierStream.anyMatch(any())).thenReturn(NEGATIVE_ANSWER);
		assertFalse(securityContext.hasToken(TOKEN));
	}

	@Test
	public void method_verifySignature_should_validate_signedJWT() throws Exception {
		sharedSecret = TOKEN.getBytes();
		signedJWT = spy(SignedJWT.parse(TOKEN));
		mockStatic(SignedJWT.class);
		
		when(SignedJWT.class, "parse", TOKEN).thenReturn(signedJWT);
		when(sessionIndentifierStream.filter(any())).thenReturn(sessionIndentifierStream);
		when(sessionIndentifierStream.findFirst()).thenReturn(sessionIdentifierOptional);
		when(sessionIdentifierOptional.get()).thenReturn(sessionIdentifier);
		when(sessionIdentifier.getSecretKey()).thenReturn(sharedSecret);
		whenNew(MACVerifier.class).withArguments(sharedSecret).thenReturn(verifier);
		when(signedJWT.verify(verifier)).thenReturn(POSITIVE_ANSWER);
		
		assertTrue(securityContext.verifySignature(TOKEN));

	}

}
