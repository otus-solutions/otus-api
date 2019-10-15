package org.ccem.otus.model.dataSources.dcm.retinography;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class DCMRetinographyDataSourceTest {

  private static final Long RECRUITMENT_NUMBER = 1063154L;
  private DCMRetinographyDataSource datasource;

  @Before
  public void setUp() {
    this.datasource = new DCMRetinographyDataSource();
    RetinographyDataSourceFilter filters = new RetinographyDataSourceFilter();
    Whitebox.setInternalState(filters, "recruitmentNumber", Long.toString(RECRUITMENT_NUMBER));
    Whitebox.setInternalState(filters, "examName", "DCMRetinography");
    Whitebox.setInternalState(filters, "sending", 0);
    Whitebox.setInternalState(filters, "eyeSelected", "left");
    Whitebox.setInternalState(datasource, "filters", filters);
  }

  @Test
  public void addResult_method() {
    ArrayList<DCMRetinographyDataSourceResult> results = datasource.getResult();

    Assert.assertNotNull(results);
  }

  @Test
  public void buildFilterToRetinography_method_should_return_string() {
    String result = this.datasource.buildFilterToRetinography(RECRUITMENT_NUMBER);

    assertTrue(result instanceof String);
  }

  @Test
  public void buildFilterToRetinography_method_should_return_string_with_values_expected() {
    String result = this.datasource.buildFilterToRetinography(RECRUITMENT_NUMBER);

    assertTrue(result.contains("\"recruitmentNumber\":\"" + Long.toString(RECRUITMENT_NUMBER) + "\""));
    assertTrue(result.contains("\"examName\":\"DCMRetinography"));
    assertTrue(result.contains("\"sending\":\"0"));
    assertTrue(result.contains("\"eyeSelected\":\"left"));
  }

}
