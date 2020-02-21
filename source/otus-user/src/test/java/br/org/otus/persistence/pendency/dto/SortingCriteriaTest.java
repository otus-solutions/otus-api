package br.org.otus.persistence.pendency.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class SortingCriteriaTest {

  private static final String FIELD_NAME = "rn";
  private static final int ASCENDING_ORDER_MODE = 1;
  private static final int DESCENDING_ORDER_MODE = -1;
  private static final int INVALID_ORDER_MODE = 0;

  private SortingCriteria sortingCriteria;

  @Before
  public void setUp(){
    sortingCriteria = new SortingCriteria(FIELD_NAME, ASCENDING_ORDER_MODE);
  }

  @Test
  public void check_getters_for_ascending_mode() {
    assertEquals(FIELD_NAME, sortingCriteria.getFieldName());
    assertEquals(ASCENDING_ORDER_MODE, sortingCriteria.getMode());
  }

  //@Test
  public void check_getters_for_descending_mode() {
    Whitebox.setInternalState(sortingCriteria, DESCENDING_ORDER_MODE);
    assertEquals(FIELD_NAME, sortingCriteria.getFieldName());
    assertEquals(DESCENDING_ORDER_MODE, sortingCriteria.getMode());
  }

  @Test
  public void isValid_method_should_return_TRUE_in_case_ascending_mode() {
    assertTrue(sortingCriteria.isValid());
  }

  @Test
  public void isValid_method_should_return_TRUE_in_case_descending_mode() {
    Whitebox.setInternalState(sortingCriteria, DESCENDING_ORDER_MODE);
    assertTrue(sortingCriteria.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_for_invalid_mode_value() {
    Whitebox.setInternalState(sortingCriteria, INVALID_ORDER_MODE);
    assertFalse(sortingCriteria.isValid());
  }
}
