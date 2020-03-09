package br.org.otus.persistence.pendency.dto;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

public class UserActivityPendencyFilterDtoTest {

  private final static String STATUS = "FINALIZED";
  private final static String INVALID_STATUS = "xxx";
  private final static String ACRONYM = "ABC";
  private final static Long RN = 1234567L;
  private final static String EXTERNAL_ID = "xwyz";
  private final static String DUE_DATE = "1900-01-01T00:00:00.000Z";
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
    Whitebox.setInternalState(userActivityPendencyFilterDto, "dueDate", DUE_DATE);
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
    assertEquals(DUE_DATE, userActivityPendencyFilterDto.getDueDate());
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

  @Test
  public void isValid_method_should_return_TRUE(){
    assertTrue(userActivityPendencyFilterDto.isValid());
  }

  @Test
  public void isValid_method_should_return_TRUE_if_status_is_null(){
    Whitebox.setInternalState(userActivityPendencyFilterDto, "status", (String) null);
    assertTrue(userActivityPendencyFilterDto.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_if_status_is_not_null_but_invalid(){
    Whitebox.setInternalState(userActivityPendencyFilterDto, "status", INVALID_STATUS);
    assertFalse(userActivityPendencyFilterDto.isValid());
  }

  @Test
  public void isValid_method_should_return_TRUE_if_requester_is_null(){
    Whitebox.setInternalState(userActivityPendencyFilterDto, "requester", (String[]) null);
    assertTrue(userActivityPendencyFilterDto.isValid());
  }

  @Test
  public void isValid_method_should_return_TRUE_if_receiver_is_null(){
    Whitebox.setInternalState(userActivityPendencyFilterDto, "receiver", (String[]) null);
    assertTrue(userActivityPendencyFilterDto.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_if_requester_is_empty(){
    Whitebox.setInternalState(userActivityPendencyFilterDto, "requester", new String[]{});
    assertFalse(userActivityPendencyFilterDto.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_if_receiver_is_empty(){
    Whitebox.setInternalState(userActivityPendencyFilterDto, "receiver", new String[]{});
    assertFalse(userActivityPendencyFilterDto.isValid());
  }

}
