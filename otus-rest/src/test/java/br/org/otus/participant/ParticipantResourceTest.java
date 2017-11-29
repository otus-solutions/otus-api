package br.org.otus.participant;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.GsonBuilder;

import br.org.otus.model.User;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.user.api.UserFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ParticipantResource.class, AuthorizationHeaderReader.class, GsonBuilder.class })
public class ParticipantResourceTest {
	private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	private static final String AUTHORIZATION_HEADER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	private static final String USER_MAIL = "otus@otus.com";
	private static final String ACRONYM = "RS";
	private static final Long RECRUITMENT_NUMBER = (long) 1063154;
	@InjectMocks
	private ParticipantResource participantResource;
	@Mock
	private ParticipantFacade participantFacade;
	@Mock
	private UserFacade userFacade;
	@Mock
	private SecurityContext securityContext;
	@Mock
	private HttpServletRequest request;
	@Mock
	private SessionIdentifier sessionIdentifier;
	@Mock
	private AuthenticationData authenticationData;
	@Mock
	private ParticipantDao participantDao;
	private User user;
	private FieldCenter fieldCenter;
	private List<Participant> participantFacadeList;
	private Participant participant;
	private InOrder inOrder;
	private GsonBuilder builder;
	
	@Before
	public void setUp(){
		fieldCenter = new FieldCenter();
		fieldCenter.setAcronym(ACRONYM);
		user = new User();
		user.setFieldCenter(fieldCenter);
		participant = new Participant(RECRUITMENT_NUMBER);
		participant.setFieldCenter(fieldCenter);
		
	}

	@Test
	public void method_getAll_should_returns_GsonParticipants() throws Exception {
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
		mockStatic(AuthorizationHeaderReader.class);
		when(AuthorizationHeaderReader.class, "readToken", TOKEN).thenReturn(AUTHORIZATION_HEADER_TOKEN);
		when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
		when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
		when(authenticationData.getUserEmail()).thenReturn(USER_MAIL);		
		when(userFacade.fetchByEmail(USER_MAIL)).thenReturn(user);
		
		builder = Mockito.spy(new GsonBuilder());
		whenNew(GsonBuilder.class).withNoArguments().thenReturn(builder);		
		participantFacadeList = Arrays.asList(participant);
		when(participantFacade.list(fieldCenter)).thenReturn(participantFacadeList);

		inOrder = Mockito.inOrder(builder);
		assertTrue(participantResource.getAll(request).contains(ACRONYM));
		verifyNew(GsonBuilder.class).withNoArguments();
		inOrder.verify(builder).registerTypeAdapter(Mockito.any(), Mockito.any());
		inOrder.verify(builder).create();
	}

	@Test
	public void method_getByRecruitmentNumber_should_returns_ParticipantInstance() {		
		when(participantFacade.getByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
		assertTrue(participantResource.getByRecruitmentNumber(RECRUITMENT_NUMBER) instanceof Participant);
	}
}
