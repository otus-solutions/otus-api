package br.org.otus.security.dtos;

import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantTempTokenRequestDtoTest {

  private static final String MODE = "sharing";
  private static final Long RN = 123456L;
  private static final String ACTIVITY_ID = "5a33cb4a28f10d1043710f7d";
  private static final String TOKEN = "1234567890";

  private ParticipantTempTokenRequestDto participantTempTokenRequestDto;

  @Before
  public void setUp(){
    participantTempTokenRequestDto = new ParticipantTempTokenRequestDto(RN, ACTIVITY_ID);
    Whitebox.setInternalState(participantTempTokenRequestDto, "token", TOKEN);
  }

  @Test
  public void uniTest_invoke_getters(){
    assertEquals(TOKEN, participantTempTokenRequestDto.getToken());
  }

  @Test
  public void uniTest_invoke_setToken(){
    final String OTHER_TOKEN = TOKEN+TOKEN;
    participantTempTokenRequestDto.setToken(OTHER_TOKEN);
    assertEquals(OTHER_TOKEN, participantTempTokenRequestDto.getToken());
  }

  @Test
  public void isValid_method_should_return_true(){
    assertTrue(participantTempTokenRequestDto.isValid());
  }

  @Test
  public void isValid_method_should_return_false_in_case_null_RN(){
    Whitebox.setInternalState(participantTempTokenRequestDto, "recruitmentNumber", (Long)null);
    assertFalse(participantTempTokenRequestDto.isValid());
  }

  @Test
  public void isValid_method_should_return_false_in_case_null_activityId(){
    Whitebox.setInternalState(participantTempTokenRequestDto, "activityId", (String)null);
    assertFalse(participantTempTokenRequestDto.isValid());
  }

  @Test
  public void should_build_correct_claims() throws ParseException {
    JWTClaimsSet jwtClaimsSet = participantTempTokenRequestDto.buildClaimSet();
    assertEquals(MODE, jwtClaimsSet.getStringClaim("mode"));
  }
}
