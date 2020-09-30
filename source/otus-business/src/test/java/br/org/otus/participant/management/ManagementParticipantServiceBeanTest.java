package br.org.otus.participant.management;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.security.dtos.ParticipantCommunicationDataDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ManagementParticipantServiceBean.class, ParticipantCommunicationDataDto.class})
public class ManagementParticipantServiceBeanTest {

  private static final String EXPECTED_LINK = "http://www.otus.com.br//#/register-password/1234567890";

  private ManagementParticipantServiceBean service = spy(new ManagementParticipantServiceBean());
  private PasswordResetRequestDto requestData = spy(new PasswordResetRequestDto());
  @Mock
  private ParticipantCommunicationDataDto participantCommunicationDataDto;
  @Mock
  private CommunicationGatewayService communicationGatewayService;

  @Before
  public void setUp() throws Exception {
    requestData.setUserEmail("otus@gmail.com");
    requestData.setToken("1234567890");
    requestData.setRedirectUrl("http://www.otus.com.br/");
  }

//  @Test
//  public void requestPasswordResetMethod_should_mount_ParticipantCommunicationDataDto_by_PasswordResetRequestDtoValues() throws Exception {
//    mockStatic(ParticipantCommunicationDataDto.class);
//    PowerMockito.whenNew(ParticipantCommunicationDataDto.class).withNoArguments().thenReturn(participantCommunicationDataDto);
//    PowerMockito.whenNew(CommunicationGatewayService.class).withAnyArguments().thenReturn(communicationGatewayService);
//
//    service.requestPasswordReset(requestData);
//    verify(participantCommunicationDataDto, times(1)).setEmail(requestData.getEmail());
//    verify(participantCommunicationDataDto, times(1)).setId(Mockito.anyString());
//    verify(participantCommunicationDataDto, times(1)).pushVariable("token", requestData.getToken());
//    verify(participantCommunicationDataDto, times(1)).pushVariable("host", String.valueOf(requestData.getRedirectUrl()));
//    verify(communicationGatewayService, times(1)).sendMail(Mockito.anyString());
//  }

  @Test(expected = EmailNotificationException.class)
  public void method_requestPasswordResetMethod_should_throw_EmailNotificationException() throws EmailNotificationException {
    service.requestPasswordReset(requestData);
  }

  @Test
  public void requestPasswordResetLinkMethod_should_mount_ParticipantCommunicationDataDto_by_PasswordResetRequestDtoValues() throws Exception {
    mockStatic(ParticipantCommunicationDataDto.class);
    PowerMockito.whenNew(ParticipantCommunicationDataDto.class).withNoArguments().thenReturn(participantCommunicationDataDto);
    Assert.assertEquals(service.requestPasswordResetLink(requestData), EXPECTED_LINK);

  }
}