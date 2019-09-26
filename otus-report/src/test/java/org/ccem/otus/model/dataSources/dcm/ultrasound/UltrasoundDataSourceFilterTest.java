package org.ccem.otus.model.dataSources.dcm.ultrasound;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class UltrasoundDataSourceFilterTest {

  private static final Long RECRUITMENT_NUMBER = 1063154L;
  private UltrasoundDataSourceFilter filter;

  @Before
  public void setup() {
    filter = new UltrasoundDataSourceFilter();
    Whitebox.setInternalState(filter, "recruitmentNumber", Long.toString(RECRUITMENT_NUMBER));
    Whitebox.setInternalState(filter, "examName", "DCMUltrasound");
    Whitebox.setInternalState(filter, "sending", 0);
  }

  @Test
  public void instance_of_class_should_have_values_expected() {
    Integer sending = 0;
    assertEquals(Long.toString(RECRUITMENT_NUMBER), filter.getRecruitmentNumber());
    assertEquals("DCMUltrasound", filter.getExamName());
    assertEquals(sending, filter.getSending());
  }

}
