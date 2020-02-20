package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyOrderDto;
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

  private UserActivityPendencyDto userActivityPendencyDto = new UserActivityPendencyDto();
  private String userActivityPendencyDtoJson;
  private UserActivityPendencyFilterDto userActivityPendencyFilterDto = new UserActivityPendencyFilterDto();
  private UserActivityPendencyOrderDto userActivityPendencyOrderDto = new UserActivityPendencyOrderDto();

  @Before
  public void setUp(){
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, "filterDto", userActivityPendencyFilterDto);
    Whitebox.setInternalState(userActivityPendencyDto, "orderDto", userActivityPendencyOrderDto);
    userActivityPendencyDtoJson = UserActivityPendencyDto.serialize(userActivityPendencyDto);
  }

  @Test
  public void unit_test_for_invoke_getters(){
    assertEquals(CURRENT_QUANTITY, userActivityPendencyDto.getCurrentQuantity());
    assertEquals(QUANTITY_TO_GET, userActivityPendencyDto.getQuantityToGet());
    assertEquals(userActivityPendencyFilterDto, userActivityPendencyDto.getFilterDto());
    assertEquals(userActivityPendencyOrderDto, userActivityPendencyDto.getOrderDto());
  }

  @Test
  public void serializeStaticMethdos_should_convert_objectModel_to_JsonString(){
    assertTrue(UserActivityPendencyDto.serialize(userActivityPendencyDto) instanceof String);
  }

  @Test
  public void deserializeStaticMethdos_should_convert_JsonString_to_objectModel(){
    assertTrue(UserActivityPendencyDto.deserialize(userActivityPendencyDtoJson) instanceof UserActivityPendencyDto);
  }

  /*
   * isValid method
   */

  @Test
  public void isValid_method_should_be_return_TRUE(){
    assertTrue(userActivityPendencyDto.isValid());
  }

  @Test
  public void isValid_method_should_be_return_TRUE_if_only_filterDto_is_null(){
    Whitebox.setInternalState(userActivityPendencyDto, "filterDto", (UserActivityPendencyFilterDto) null);
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "filterDto", userActivityPendencyFilterDto);
    assertTrue(isValid);
  }

  @Test
  public void isValid_method_should_be_return_TRUE_if_only_orderDto_is_null(){
    Whitebox.setInternalState(userActivityPendencyDto, "orderDto", (UserActivityPendencyOrderDto) null);
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "orderDto", userActivityPendencyOrderDto);
    assertTrue(isValid);
  }

}
