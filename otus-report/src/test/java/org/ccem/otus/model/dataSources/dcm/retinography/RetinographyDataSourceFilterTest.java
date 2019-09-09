package org.ccem.otus.model.dataSources.dcm.retinography;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class RetinographyDataSourceFilterTest {

  private static final Long RECRUITMENT_NUMBER = 1063154L;

  private RetinographyDataSourceFilter filter;

  @Before
  public void setup() {
    filter = new RetinographyDataSourceFilter();
    Whitebox.setInternalState(filter, "recruitmentNumber", Long.toString(RECRUITMENT_NUMBER));
    Whitebox.setInternalState(filter, "examName", "Retinography");
    Whitebox.setInternalState(filter, "sending", 0);
    Whitebox.setInternalState(filter, "eyeSelected", "left");
  }

  @Test
  public void instance_of_class_should_have_values_expected() {
    assertEquals(Long.toString(RECRUITMENT_NUMBER), filter.getRecruitmentNumber());
    assertEquals("Retinography", filter.getExamName());
    assertEquals(new Integer(0), filter.getSending());
    assertEquals("left", filter.getEyeSelected());
  }

}
