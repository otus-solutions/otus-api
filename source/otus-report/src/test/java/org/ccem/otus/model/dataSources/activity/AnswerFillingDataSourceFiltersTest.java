package org.ccem.otus.model.dataSources.activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class AnswerFillingDataSourceFiltersTest {
  private static final String ACRONYM = "ACTA";
  private static final Integer VERSION = 2;
  private static final String CATEGORY = "C0";
  private AnswerFillingDataSourceFilters filters;

  @Before
  public void setUp() throws Exception {
    filters = new AnswerFillingDataSourceFilters();
    Whitebox.setInternalState(filters, "acronym", ACRONYM);
    Whitebox.setInternalState(filters, "version", VERSION);
    Whitebox.setInternalState(filters, "category", "C0");
  }

  @Test
  public void getAcronym() {
    assertEquals(ACRONYM, filters.getAcronym());
  }

  @Test
  public void getVersion() {
    assertEquals(VERSION, filters.getVersion());
  }

  @Test
  public void getCategory() {
    assertEquals(CATEGORY, filters.getCategory());
  }
}