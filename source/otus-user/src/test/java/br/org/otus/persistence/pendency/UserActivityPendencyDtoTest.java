package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyDtoTest {

  private final static int CURRENT_QUANTITY = 100;
  private final static int QUANTITY_TO_GET = 50;
  private static final String[] FIELDS_TO_ORDER = new String[] { "requester", "acronym" };
  private static final int ORDER_MODE = 1;

  private UserActivityPendencyDto userActivityPendencyDto = new UserActivityPendencyDto();
  private String userActivityPendencyDtoJson;
  private UserActivityPendencyFilterDto userActivityPendencyFilterDto = new UserActivityPendencyFilterDto();

  @Before
  public void setUp(){
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ORDER_MODE);
    Whitebox.setInternalState(userActivityPendencyDto, "filterDto", userActivityPendencyFilterDto);

    userActivityPendencyDtoJson = UserActivityPendencyDto.serialize(userActivityPendencyDto);
  }

  @Test
  public void unit_test_for_invoke_getters(){
    assertEquals(CURRENT_QUANTITY, userActivityPendencyDto.getCurrentQuantity());
    assertEquals(QUANTITY_TO_GET, userActivityPendencyDto.getQuantityToGet());
    assertArrayEquals(FIELDS_TO_ORDER, userActivityPendencyDto.getFieldsToOrder());
    assertEquals(ORDER_MODE, userActivityPendencyDto.getOrderMode());
    assertEquals(userActivityPendencyFilterDto, userActivityPendencyDto.getFilterDto());
  }

  @Test
  public void serializeStaticMethdos_should_convert_objectModel_to_JsonString(){
    assertTrue(UserActivityPendencyDto.serialize(userActivityPendencyDto) instanceof String);
  }

  @Test
  public void deserializeStaticMethdos_should_convert_JsonString_to_objectModel(){
    assertTrue(UserActivityPendencyDto.deserialize(userActivityPendencyDtoJson) instanceof UserActivityPendencyDto);
  }

}
