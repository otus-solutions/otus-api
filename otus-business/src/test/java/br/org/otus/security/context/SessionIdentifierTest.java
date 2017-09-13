package br.org.otus.security.context;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.ccem.auditor.model.SessionLog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.org.otus.security.dtos.AuthenticationData;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SessionIdentifier.class, SignedJWT.class })
public class SessionIdentifierTest {
	private static final String USER = "otus";
	private static final String MODE = "client";
	private static final String SECRET_KEY = "#otus123";
	private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	@InjectMocks
	private SessionIdentifier sessionIdentifier;
	@Mock
	private AuthenticationData authenticationData;
	@Mock
	private SessionLog sessionLog;
	private JWTClaimsSet jwtClaimsSet;
	private SignedJWT signedJWT;
	private byte[] secretKey;

	@Before
	public void setUp() throws Exception {
		

	}

	@Test
	public void method_buildLog_should_return_sessionLog() throws Exception {
		when(authenticationData.getUserEmail()).thenReturn(USER);
		when(authenticationData.getKey()).thenReturn(SECRET_KEY);
		when(authenticationData.getMode()).thenReturn(MODE);
		whenNew(SessionLog.class).withAnyArguments().thenReturn(sessionLog);
		assertTrue(sessionIdentifier.buildLog() instanceof SessionLog);
		verify(sessionLog).setToken(anyString());
		verify(sessionLog).setSecretKey(anyObject());
		verify(sessionLog).setRequestAddress(anyObject());

	}

	@Test
	public void method_GetClaims() throws Exception {
		secretKey = TOKEN.getBytes();
		sessionIdentifier = PowerMockito.spy(new SessionIdentifier(TOKEN, secretKey, authenticationData));
		signedJWT = PowerMockito.spy(SignedJWT.parse(TOKEN));
		mockStatic(SignedJWT.class);
		when(SignedJWT.class, "parse", TOKEN).thenReturn(signedJWT);
		assertTrue(sessionIdentifier.getClaims() instanceof JWTClaimsSet);
		verify(signedJWT).getJWTClaimsSet();

	}

}
