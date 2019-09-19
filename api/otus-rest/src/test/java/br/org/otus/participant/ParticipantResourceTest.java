package br.org.otus.participant;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
@PrepareForTest({ AuthorizationHeaderReader.class, GsonBuilder.class })
public class ParticipantResourceTest {
  private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String AUTHORIZATION_HEADER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String USER_MAIL = "otus@otus.com";
  private static final String ACRONYM = "RS";
  private static final String PARTICIPANT = "{\n" + "    \"data\": {\n" + "        \"recruitmentNumber\": 1366552,\n"
      + "        \"name\": \"Tiago Matana\",\n" + "        \"sex\": \"M\",\n" + "        \"birthdate\": {\n"
      + "            \"objectType\": \"ImmutableDate\",\n" + "            \"value\": \"1954-09-20 00:00:00.000\"\n"
      + "        },\n" + "        \"fieldCenter\": {\n" + "            \"name\": \"Sao Paulo\",\n"
      + "            \"code\": 6,\n" + "            \"acronym\": \"SP\",\n"
      + "            \"backgroundColor\": \"rgba(54, 162, 235, 0.2)\",\n"
      + "            \"borderColor\": \"rgba(54, 162, 235, 1)\"\n" + "        },\n" + "        \"late\": false\n"
      + "    }\n" + "}";
  private static final String PARTICIPANT_INVALID = "{}";
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
  public void setUp() throws Exception {
    fieldCenter = new FieldCenter();
    fieldCenter.setAcronym(ACRONYM);
    user = new User();
    user.setFieldCenter(fieldCenter);
    participant = new Participant(RECRUITMENT_NUMBER);
    participant.setFieldCenter(fieldCenter);

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
    mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.class, "readToken", TOKEN).thenReturn(AUTHORIZATION_HEADER_TOKEN);
    when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(USER_MAIL);
    when(userFacade.fetchByEmail(USER_MAIL)).thenReturn(user);

  }

  @Test
  public void method_getAll_should_returns_GsonParticipants() throws Exception {
    participantFacadeList = Arrays.asList(participant);
    when(participantFacade.list(fieldCenter)).thenReturn(participantFacadeList);
    assertTrue(participantResource.getAll(request).contains(ACRONYM));
    verify(participantFacade, times(1)).list(fieldCenter);
  }

  @Test
  public void method_getByRecruitmentNumber_should_returns_ParticipantInstance() {
    when(participantFacade.getByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
    assertTrue(participantResource.getByRecruitmentNumber(RECRUITMENT_NUMBER) instanceof Participant);
    verify(participantFacade, times(1)).getByRecruitmentNumber(RECRUITMENT_NUMBER);
  }

  @Test
  public void method_create_should_returns_ParticipantInstance() throws Exception {
    when(participantFacade.create(PARTICIPANT)).thenReturn(participant);
    assertTrue(participantResource.create(request, PARTICIPANT) instanceof String);
    verify(participantFacade, times(1)).create(PARTICIPANT);
  }
}
