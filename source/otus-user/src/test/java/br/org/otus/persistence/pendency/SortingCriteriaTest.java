package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.SortingCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class SortingCriteriaTest {

  private static final String FIELD_NAME = "rn";
  private static final int ASCENDING_ORDER_MODE = 1;
  private static final int INVALID_ORDER_MODE = 0;

  private SortingCriteria sortingCriteria;

  @Before
  public void setUp() throws DataFormatException {
    sortingCriteria = new SortingCriteria(FIELD_NAME, ASCENDING_ORDER_MODE);
  }

  @Test
  public void check_getters(){
    assertEquals(FIELD_NAME, sortingCriteria.getFieldName());
    assertEquals(ASCENDING_ORDER_MODE, sortingCriteria.getMode());
  }

  @Test(expected = DataFormatException.class)
  public void constructor_not_should_accept_invalid_order_mode() throws DataFormatException {
    new SortingCriteria(FIELD_NAME, INVALID_ORDER_MODE);
  }
}
