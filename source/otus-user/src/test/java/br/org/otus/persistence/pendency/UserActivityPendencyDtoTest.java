package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import static org.powermock.api.mockito.PowerMockito.when;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyDtoTest {

  private final static int CURRENT_QUANTITY = 100;
  private final static int QUANTITY_TO_GET = 50;
  private final static String FILTER_DTO_FIELD_NAME = "filterDto";
  private final static String ORDER_DTO_FIELD_NAME = "orderDto";

  @InjectMocks
  private UserActivityPendencyDto userActivityPendencyDto = PowerMockito.spy(new UserActivityPendencyDto());

  private UserActivityPendencyFilterDto filterDto = PowerMockito.spy(new UserActivityPendencyFilterDto());
  private UserActivityPendencyOrderDto orderDto = PowerMockito.spy(new UserActivityPendencyOrderDto());

  @Before
  public void setUp(){
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, FILTER_DTO_FIELD_NAME, filterDto);
    Whitebox.setInternalState(userActivityPendencyDto, ORDER_DTO_FIELD_NAME, orderDto);
  }

  @Test
  public void unit_test_for_invoke_getters(){
    assertEquals(CURRENT_QUANTITY, userActivityPendencyDto.getCurrentQuantity());
    assertEquals(QUANTITY_TO_GET, userActivityPendencyDto.getQuantityToGet());
    assertEquals(filterDto, userActivityPendencyDto.getFilterDto());
    assertEquals(orderDto, userActivityPendencyDto.getOrderDto());
  }

  @Test
  public void isValid_method_should_be_return_TRUE(){
    assertTrue(userActivityPendencyDto.isValid());
  }

  @Test
  public void isValid_method_should_be_return_TRUE_if_only_filterDto_is_null(){
    Whitebox.setInternalState(userActivityPendencyDto, FILTER_DTO_FIELD_NAME, (UserActivityPendencyFilterDto) null);
    assertTrue(userActivityPendencyDto.isValid());
  }

  @Test
  public void isValid_method_should_be_return_TRUE_if_only_orderDto_is_null(){
    Whitebox.setInternalState(userActivityPendencyDto, ORDER_DTO_FIELD_NAME, (UserActivityPendencyOrderDto) null);
    assertTrue(userActivityPendencyDto.isValid());
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_filterDto_is_invalid() throws Exception {
    when(filterDto, "isValid").thenReturn(false);
    assertFalse(userActivityPendencyDto.isValid());
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_orderDto_is_invalid() throws Exception {
    when(orderDto, "isValid").thenReturn(false);
    assertFalse(userActivityPendencyDto.isValid());
  }

  @Test
  public void serializeStaticMethdos_should_convert_objectModel_to_JsonString(){
    assertTrue(UserActivityPendencyDto.serialize(new UserActivityPendencyDto()) instanceof String);
  }

  @Test
  public void deserializeStaticMethdos_should_convert_JsonString_to_objectModel(){
    assertTrue(UserActivityPendencyDto.deserialize("{}") instanceof UserActivityPendencyDto);
  }

}
