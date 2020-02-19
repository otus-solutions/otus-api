package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.SortingCriteria;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.zip.DataFormatException;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyDtoTest {

  private final static int CURRENT_QUANTITY = 100;
  private final static int QUANTITY_TO_GET = 50;
  private static final String[] FIELDS_TO_ORDER = new String[] { "requester", "acronym" };
  private static final Integer ASCENDING_ORDER_MODE = 1;
  private static final Integer DESCENDING_ORDER_MODE = -1;
  private static final Integer INVALID_ORDER_MODE = 0;

  private UserActivityPendencyDto userActivityPendencyDto = new UserActivityPendencyDto();
  private String userActivityPendencyDtoJson;
  private UserActivityPendencyFilterDto userActivityPendencyFilterDto = new UserActivityPendencyFilterDto();

  @Before
  public void setUp(){
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ASCENDING_ORDER_MODE);
    Whitebox.setInternalState(userActivityPendencyDto, "filterDto", userActivityPendencyFilterDto);
    userActivityPendencyDtoJson = UserActivityPendencyDto.serialize(userActivityPendencyDto);
  }

  @Test
  public void unit_test_for_invoke_getters(){
    assertEquals(CURRENT_QUANTITY, userActivityPendencyDto.getCurrentQuantity());
    assertEquals(QUANTITY_TO_GET, userActivityPendencyDto.getQuantityToGet());
    assertArrayEquals(FIELDS_TO_ORDER, userActivityPendencyDto.getFieldsToOrder());
    assertEquals(ASCENDING_ORDER_MODE, userActivityPendencyDto.getOrderMode());
    assertEquals(userActivityPendencyFilterDto, userActivityPendencyDto.getFilterDto());
  }

  @Test
  public void getSortingCriteria_method_should_return_array_with_fieldsToOrder_and_orderMode() throws DataFormatException {
    SortingCriteria[] sortingCriteria = userActivityPendencyDto.getSortingCriteria();
    assertEquals(FIELDS_TO_ORDER.length, sortingCriteria.length);
    for (int i = 0; i < FIELDS_TO_ORDER.length; i++) {
      assertEquals(FIELDS_TO_ORDER[i], sortingCriteria[i].getFieldName());
      assertEquals(ASCENDING_ORDER_MODE, new Integer(sortingCriteria[i].getMode()));
    }
  }

  @Test
  public void getSortingCriteria_method_should_return_null_in_case_fieldsToOrder_null() throws DataFormatException {
    String[] fieldsToOrderAsNull = null;
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", fieldsToOrderAsNull);
    assertNull(userActivityPendencyDto.getSortingCriteria());
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
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
  public void isValid_method_should_be_return_TRUE_for_descending_order_mode(){
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", DESCENDING_ORDER_MODE);
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ASCENDING_ORDER_MODE);
    assertTrue(isValid);
  }

  @Test
  public void isValid_method_should_be_return_TRUE_if_fieldsToOrder_and_orderMode_are_null(){
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", (Object[]) null);
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", (Integer) null);
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ASCENDING_ORDER_MODE);
    assertTrue(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_fieldsToOrder_null_and_orderMode_not_null(){
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", (Object[]) null);
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
    assertFalse(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_fieldsToOrder_not_null_and_orderMode_null(){
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", (Integer) null);
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ASCENDING_ORDER_MODE);
    assertFalse(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_nothing_about_order_is_null_and_fieldsToOrder_is_empty(){
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", new String[]{});
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
    assertFalse(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_nothing_about_order_is_null_and_orderMode_is_invalid(){
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", INVALID_ORDER_MODE);
    Boolean isValid = userActivityPendencyDto.isValid();
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ASCENDING_ORDER_MODE);
    assertFalse(isValid);
  }

}
