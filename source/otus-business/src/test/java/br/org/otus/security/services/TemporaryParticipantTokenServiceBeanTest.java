package br.org.otus.security.services;

import br.org.otus.security.dtos.ParticipantTempTokenRequestDto;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class TemporaryParticipantTokenServiceBeanTest {

  private static final byte[] SECRET_KEY = new byte[2];
  private static final String TOKEN = "abc";

  @InjectMocks
  private TemporaryParticipantTokenServiceBean temporaryParticipantTokenServiceBean;
  @Mock
  private SecurityContextService securityContextService;

  private ParticipantTempTokenRequestDto participantTempTokenRequestDto;

  @Before
  public void setUp() throws TokenException {
    participantTempTokenRequestDto = new ParticipantTempTokenRequestDto(null, null);

    when(securityContextService.generateSecretKey()).thenReturn(SECRET_KEY);
    when(securityContextService.generateToken(participantTempTokenRequestDto, SECRET_KEY)).thenReturn(TOKEN);
  }

  @Test
  public void generateTempToken_should_build_token() throws TokenException {
    assertNotNull(temporaryParticipantTokenServiceBean.generateTempToken(participantTempTokenRequestDto));
  }
}
