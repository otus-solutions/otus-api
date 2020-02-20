package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

  private UserActivityPendencyDto userActivityPendencyDto = new UserActivityPendencyDto();
  private String userActivityPendencyDtoJson;

  @Mock
  private UserActivityPendencyFilterDto userActivityPendencyFilterDto = new UserActivityPendencyFilterDto();
  @Mock
  private UserActivityPendencyOrderDto userActivityPendencyOrderDto = new UserActivityPendencyOrderDto();

  @Before
  public void setUp(){
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, FILTER_DTO_FIELD_NAME, userActivityPendencyFilterDto);
    Whitebox.setInternalState(userActivityPendencyDto, ORDER_DTO_FIELD_NAME, userActivityPendencyOrderDto);
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
  public void isValid_method_should_be_return_FALSE_if_filterDto_is_invalid(){
    //TODO
//    when(userActivityPendencyFilterDto.isValid()).thenReturn(false);
//    assertFalse(userActivityPendencyDto.isValid());
  }

}
