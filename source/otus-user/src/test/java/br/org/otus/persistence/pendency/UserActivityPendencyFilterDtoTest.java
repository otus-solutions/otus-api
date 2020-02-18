package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

public class UserActivityPendencyFilterDtoTest {

  private final static String STATUS = "FINALIZED";
  private final static String ACRONYM = "ABC";
  private final static Long RN = 1234567L;
  private final static String EXTERNAL_ID = "xwyz";
  private final static String[] REQUESTER = new String[]{"requester1@otus.com", "requester2@otus.com"};
  private final static String[] RECEIVER = new String[]{"receiver1@otus.com", "receiver2@otus.com"};

  private UserActivityPendencyFilterDto userActivityPendencyFilterDto = new UserActivityPendencyFilterDto();
  private String userActivityPendencyFilterDtoJson;

  @Before
  public void setUp(){
    Whitebox.setInternalState(userActivityPendencyFilterDto, "status", STATUS);
    Whitebox.setInternalState(userActivityPendencyFilterDto, "acronym", ACRONYM);
    Whitebox.setInternalState(userActivityPendencyFilterDto, "rn", RN);
    Whitebox.setInternalState(userActivityPendencyFilterDto, "externalID", EXTERNAL_ID);
    Whitebox.setInternalState(userActivityPendencyFilterDto, "requester", REQUESTER);
    Whitebox.setInternalState(userActivityPendencyFilterDto, "receiver", RECEIVER);
    userActivityPendencyFilterDtoJson = UserActivityPendencyFilterDto.serialize(userActivityPendencyFilterDto);
  }

  @Test
  public void unit_test_for_invoke_getters(){
    assertEquals(STATUS, userActivityPendencyFilterDto.getStatus());
    assertEquals(ACRONYM, userActivityPendencyFilterDto.getAcronym());
    assertEquals(RN, userActivityPendencyFilterDto.getRn());
    assertEquals(EXTERNAL_ID, userActivityPendencyFilterDto.getExternalID());
    assertArrayEquals(REQUESTER, userActivityPendencyFilterDto.getRequesters());
    assertArrayEquals(RECEIVER, userActivityPendencyFilterDto.getReceivers());
  }

  @Test
  public void serializeStaticMethod_should_convert_objectModel_to_JsonString(){
    assertTrue(UserActivityPendencyFilterDto.serialize(userActivityPendencyFilterDto) instanceof String);
  }

  @Test
  public void deserializeStaticMethod_should_convert_JsonString_to_objectModel(){
    assertTrue(UserActivityPendencyFilterDto.deserialize(userActivityPendencyFilterDtoJson) instanceof UserActivityPendencyFilterDto);
  }
}
