package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.SortingCriteria;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.zip.DataFormatException;

import static org.junit.Assert.*;

public class UserActivityPendencyOrderDtoTest {

  private static final String FIELDS_TO_ODER_FIELD_NAME =  "fieldsToOrder";
  private static final String ODER_MODE_FIELD_NAME =  "mode";

  private static final String[] FIELDS_TO_ORDER = new String[] { "requester", "acronym" };
  private static final Integer ASCENDING_ORDER_MODE = 1;
  private static final Integer DESCENDING_ORDER_MODE = -1;
  private static final Integer INVALID_ORDER_MODE = 0;

  private UserActivityPendencyOrderDto userActivityPendencyOrderDto = new UserActivityPendencyOrderDto();
  private String userActivityPendencyOrderDtoJson;

  @Before
  public void setUp(){

    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, FIELDS_TO_ORDER);
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, ASCENDING_ORDER_MODE);

    userActivityPendencyOrderDtoJson = UserActivityPendencyOrderDto.serialize(userActivityPendencyOrderDto);
  }

  @Test
  public void unit_test_for_invoke_getters(){
    assertArrayEquals(FIELDS_TO_ORDER, userActivityPendencyOrderDto.getFieldsToOrder());
    assertEquals(ASCENDING_ORDER_MODE, userActivityPendencyOrderDto.getOrderMode());
  }

  @Test
  public void getSortingCriteria_method_should_return_array_with_fieldsToOrder_and_orderMode() throws DataFormatException {
    SortingCriteria[] sortingCriteria = userActivityPendencyOrderDto.getSortingCriteria();
    assertEquals(FIELDS_TO_ORDER.length, sortingCriteria.length);
    for (int i = 0; i < FIELDS_TO_ORDER.length; i++) {
      assertEquals(FIELDS_TO_ORDER[i], sortingCriteria[i].getFieldName());
      assertEquals(ASCENDING_ORDER_MODE, new Integer(sortingCriteria[i].getMode()));
    }
  }

  @Test
  public void getSortingCriteria_method_should_return_null_in_case_fieldsToOrder_null() throws DataFormatException {
    String[] fieldsToOrderAsNull = null;
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, fieldsToOrderAsNull);
    assertNull(userActivityPendencyOrderDto.getSortingCriteria());
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, FIELDS_TO_ORDER);
  }

  @Test
  public void isValid_method_should_be_return_TRUE_for_descending_order_mode(){
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, DESCENDING_ORDER_MODE);
    Boolean isValid = userActivityPendencyOrderDto.isValid();
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, ASCENDING_ORDER_MODE);
    assertTrue(isValid);
  }

  @Test
  public void isValid_method_should_be_return_TRUE_if_fieldsToOrder_and_orderMode_are_null(){
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, (Object[]) null);
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, (Integer) null);
    Boolean isValid = userActivityPendencyOrderDto.isValid();
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, FIELDS_TO_ORDER);
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, ASCENDING_ORDER_MODE);
    assertTrue(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_fieldsToOrder_null_and_orderMode_not_null(){
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, (Object[]) null);
    Boolean isValid = userActivityPendencyOrderDto.isValid();
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, FIELDS_TO_ORDER);
    assertFalse(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_fieldsToOrder_not_null_and_orderMode_null(){
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, (Integer) null);
    Boolean isValid = userActivityPendencyOrderDto.isValid();
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, ASCENDING_ORDER_MODE);
    assertFalse(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_nothing_about_order_is_null_and_fieldsToOrder_is_empty(){
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, new String[]{});
    Boolean isValid = userActivityPendencyOrderDto.isValid();
    Whitebox.setInternalState(userActivityPendencyOrderDto, FIELDS_TO_ODER_FIELD_NAME, FIELDS_TO_ORDER);
    assertFalse(isValid);
  }

  @Test
  public void isValid_method_should_be_return_FALSE_if_nothing_about_order_is_null_and_orderMode_is_invalid(){
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, INVALID_ORDER_MODE);
    Boolean isValid = userActivityPendencyOrderDto.isValid();
    Whitebox.setInternalState(userActivityPendencyOrderDto, ODER_MODE_FIELD_NAME, ASCENDING_ORDER_MODE);
    assertFalse(isValid);
  }
}
