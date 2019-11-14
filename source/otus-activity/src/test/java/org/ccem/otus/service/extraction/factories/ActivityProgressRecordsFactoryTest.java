package org.ccem.otus.service.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActivityProgressRecordsFactoryTest {
  
  private static final Long RECRUITMENT_NUMBER = 1015533L;
  private static final String STATUS = "FINALIZED";
  private static final String ACRONYM = "CSJ";
  private static final String STATUS_DATE = "2018-10-15T11:40:05.282Z";
  private static final String OBSERVATION = "";

  private ActivityProgressRecordsFactory factory;
  private LinkedList<ActivityProgressResultExtraction> records;

  @Before
  public void setup() {
    ActivityProgressResultExtraction result = ActivityProgressResultExtraction.deserialize("{\n" + 
      "  \"rn\":1015533,\n" + 
      "  \"acronym\":\"CSJ\",\n" + 
      "  \"status\"=\"FINALIZED\",\n" + 
      "  \"statusDate\":\"2018-10-15T11:40:05.282Z\",\n" + 
      "  \"observation\":\"\"\n" + 
      "}");
    this.records = new LinkedList<>();
    this.records.add(result);
    this.factory = new ActivityProgressRecordsFactory(records);
  }

  @Test
  public void getRecords_method_should_contain_expected_values() {
    List<List<Object>> extraction = this.factory.getRecords();
   
    List<Object> results = extraction.get(0);
       
    Assert.assertEquals(RECRUITMENT_NUMBER.toString(), results.get(0));
    Assert.assertEquals(ACRONYM.toString(), results.get(1));
    Assert.assertEquals(STATUS, results.get(2));
    Assert.assertEquals(STATUS_DATE, results.get(3));
    Assert.assertEquals(OBSERVATION, results.get(4));
  }

}