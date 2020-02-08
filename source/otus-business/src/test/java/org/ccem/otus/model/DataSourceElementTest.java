package org.ccem.otus.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class DataSourceElementTest {

  private static final String value = "TESTE";
  private static final String extractionValue = "1";

  private DataSourceElement dataSourceElement;


  @Before
  public void setUp() {
    dataSourceElement = new DataSourceElement(value, extractionValue);
  }

  @Test
  public void should_method_equals_return_true() {
    DataSourceElement ds = new DataSourceElement(value, extractionValue);
    assertEquals(ds, dataSourceElement);
    assertEquals(dataSourceElement.equals(ds), Boolean.TRUE);
  }

  @Test
  public void should_method_equals_return_false() {
    DataSourceElement ds = new DataSourceElement();
    assertNotEquals(ds, dataSourceElement);
    assertEquals(dataSourceElement.equals(ds), Boolean.FALSE);
  }

}