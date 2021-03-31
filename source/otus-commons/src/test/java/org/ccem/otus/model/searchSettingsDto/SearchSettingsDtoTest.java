package org.ccem.otus.model.searchSettingsDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
public class SearchSettingsDtoTest {

  private static final int CURRENT_QUANTITY = 0;
  private static final int INVALID_CURRENT_QUANTITY = -1;
  private static final int QUANTITY_TO_GET = 5;
  private static final int INVALID_QUANTITY_TO_GET = 0;
  private static final Enum ORDER_FIELDS_OPTIONS = null;
  private static final String JSON = "{}";

  private SearchSettingsDto searchSettingsDto;
  @Mock
  private OrderSettingsDto orderSettingsDto;

  @Before
  public void setUp(){
    searchSettingsDto = new SearchSettingsDto();
    Whitebox.setInternalState(searchSettingsDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(searchSettingsDto, "quantityToGet", QUANTITY_TO_GET);
  }

  @Test
  public void constructor_with_args_should_set_oderSettingDto(){
    searchSettingsDto = new SearchSettingsDto(ORDER_FIELDS_OPTIONS);
    assertNotNull(searchSettingsDto.getOrder());
  }

  @Test
  public void getters_check(){
    assertEquals(CURRENT_QUANTITY, searchSettingsDto.getCurrentQuantity());
    assertEquals(QUANTITY_TO_GET, searchSettingsDto.getQuantityToGet());
    assertNull(searchSettingsDto.getOrder());
  }

  @Test
  public void isValid_method_should_return_true_with_null_orderSettingsDto(){
    assertTrue(searchSettingsDto.isValid());
  }

  @Test
  public void isValid_method_should_return_true_with_NOT_null_orderSettingsDto(){
    Whitebox.setInternalState(searchSettingsDto, "order", orderSettingsDto);
    doReturn(true).when(orderSettingsDto).isValid();
    assertTrue(searchSettingsDto.isValid());
  }

  @Test
  public void isValid_method_should_return_false_in_case_invalid_currentQuantity(){
    Whitebox.setInternalState(searchSettingsDto, "currentQuantity", INVALID_CURRENT_QUANTITY);
    assertFalse(searchSettingsDto.isValid());
  }

  @Test
  public void isValid_method_should_return_false_in_case_invalid_quantityToGet(){
    Whitebox.setInternalState(searchSettingsDto, "quantityToGet", INVALID_QUANTITY_TO_GET);
    assertFalse(searchSettingsDto.isValid());
  }

  @Test
  public void isValid_method_should_return_false_in_case_invalid_orderSettingsDto(){
    Whitebox.setInternalState(searchSettingsDto, "order", orderSettingsDto);
    doReturn(false).when(orderSettingsDto).isValid();
    assertFalse(searchSettingsDto.isValid());
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    assertTrue(SearchSettingsDto.deserialize(JSON) instanceof SearchSettingsDto);
  }

}
