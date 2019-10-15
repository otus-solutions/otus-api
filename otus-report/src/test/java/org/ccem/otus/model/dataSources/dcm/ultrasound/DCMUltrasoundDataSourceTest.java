package org.ccem.otus.model.dataSources.dcm.ultrasound;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class DCMUltrasoundDataSourceTest {

  private static final Long RECRUITMENT_NUMBER = 1063154L;
  private DCMUltrasoundDataSource datasource;

  @Before
  public void setUp() {
    this.datasource = new DCMUltrasoundDataSource();
    UltrasoundDataSourceFilter filters = new UltrasoundDataSourceFilter();
    Whitebox.setInternalState(filters, "recruitmentNumber", Long.toString(RECRUITMENT_NUMBER));
    Whitebox.setInternalState(filters, "examName", "DCMUltrasound");
    Whitebox.setInternalState(filters, "sending", 0);
    Whitebox.setInternalState(datasource, "filters", filters);
  }

  @Test
  public void addResult_method() {
    ArrayList<DCMUltrasoundDataSourceResult> results = datasource.getResult();

    Assert.assertNotNull(results);
  }

  @Test
  public void buildFilterToUltrasound_method_should_return_string() {
    String result = this.datasource.buildFilterToUltrasound(RECRUITMENT_NUMBER);

    assertTrue(result instanceof String);
  }

  @Test
  public void buildFilterToUltrasound_method_should_return_string_with_values_expected() {
    String result = this.datasource.buildFilterToUltrasound(RECRUITMENT_NUMBER);

    assertTrue(result.contains("\"recruitmentNumber\":\"" + Long.toString(RECRUITMENT_NUMBER) + "\""));
    assertTrue(result.contains("\"examName\":\"DCMUltrasound"));
    assertTrue(result.contains("\"sending\":\"0"));
  }
  
}
