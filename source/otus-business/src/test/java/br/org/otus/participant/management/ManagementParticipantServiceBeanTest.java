package br.org.otus.participant.management;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.resource.CommunicationMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.security.dtos.ParticipantCommunicationDataDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ManagementParticipantServiceBean.class, CommunicationGatewayService.class, CommunicationMicroServiceResources.class})
public class ManagementParticipantServiceBeanTest {

  private ManagementParticipantServiceBean service = spy(new ManagementParticipantServiceBean());
  private PasswordResetRequestDto requestData = spy(new PasswordResetRequestDto());
  private ParticipantCommunicationDataDto participantCommunicationDataDto = spy(new ParticipantCommunicationDataDto());
  @Mock
  private CommunicationGatewayService communicationGatewayService = spy(new CommunicationGatewayService());
//  @Mock
//  private CommunicationGatewayService communicationGatewayService;

  private GatewayResponse gatewayResponse = spy(new GatewayResponse());

  @Before
  public void setUp() throws Exception {
    requestData.setUserEmail("otus@gmail.com");
    requestData.setToken("1234567890");
    requestData.setRedirectUrl("http://www.otus.com.br/");

  }
//TODO: Precisamos  bloquear a chamada do gateway

  @Test
  public void requestPasswordResetMethod_should_mount_ParticipantCommunicationDataDto_by_PasswordResetRequestDtoValues() throws Exception {
//      mockStatic(CommunicationGatewayService.class);
//      PowerMockito.whenNew(CommunicationGatewayService.class).withAnyArguments().thenReturn(communicationGatewayService);
//      PowerMockito.doNothing().when(CommunicationMicroServiceResources.class, "getCommunicationAddress", Mockito.anyObject(), Mockito.anyObject());

//      PowerMockito.mockStatic(CommunicationGatewayService.class);
//      PowerMockito.doNothing().when(communicationGatewayService.sendMail(Mockito.anyString()));

//      PowerMockito.when(communicationGatewayService.sendMail(Mockito.anyString())).thenReturn(gatewayResponse.buildSuccess());
//      service.requestPasswordReset(requestData);
  }
}