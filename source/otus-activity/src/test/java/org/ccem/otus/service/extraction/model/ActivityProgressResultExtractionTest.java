package org.ccem.otus.service.extraction.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class ActivityProgressResultExtractionTest {

  private ActivityProgressResultExtraction result;

  @Test
  public void serialize_method_should_return_JSON_with_elements_expected() {
    this.result = new ActivityProgressResultExtraction();
    Whitebox.setInternalState(this.result, "rn", new Long(3051442));
    Whitebox.setInternalState(this.result, "acronym", "CSJ");
    Whitebox.setInternalState(this.result, "status", "FINALIZADO");
    Whitebox.setInternalState(this.result, "statusDate", "2018-10-15T11:40:05.282Z");
    Whitebox.setInternalState(this.result, "observation", "");
    String json = ActivityProgressResultExtraction.serialize(this.result);

    assertTrue(json.contains("3051442"));
    assertTrue(json.contains("CSJ"));
    assertTrue(json.contains("FINALIZADO"));
    assertTrue(json.contains("2018-10-15T11:40:05.282Z"));
  }

  @Test
  public void deserialize_method_should_return_elements_with_values_expected() {
    this.result = ActivityProgressResultExtraction.deserialize("{\n" + 
        "  \"rn\":5006259,\n" + 
        "  \"status\"=\"FINALIZED\",\n" + 
        "  \"acronym\":\"CSJ\",\n" + 
        "  \"statusDate\":\"2018-10-15T11:40:05.282Z\",\n" + 
        "  \"observation\":\"\"\n" + 
        "}");

    assertEquals("5006259", this.result.getRecruitmentNumber().toString());
    assertEquals("CSJ", this.result.getAcronym());
    assertEquals("FINALIZED", this.result.getStatus());
  }

}