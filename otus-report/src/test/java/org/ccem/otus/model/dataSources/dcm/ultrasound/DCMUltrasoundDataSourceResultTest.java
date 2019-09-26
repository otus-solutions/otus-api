package org.ccem.otus.model.dataSources.dcm.ultrasound;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.ccem.otus.model.dataSources.dcm.retinography.DCMRetinographyDataSourceResult;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class DCMUltrasoundDataSourceResultTest {

  private DCMUltrasoundDataSourceResult result;

  @Before
  public void setUp() {
    this.result = new DCMUltrasoundDataSourceResult();
    Whitebox.setInternalState(this.result, "date", LocalDateTime.now());
    Whitebox.setInternalState(this.result, "eye", "left");
    Whitebox.setInternalState(this.result, "result", new byte[10]);
  }

  @Test
  public void serialize_method_should_return_string_with_values_expected() {
    String serialized = DCMUltrasoundDataSourceResult.serialize(this.result);

    assertTrue(serialized.contains("\"date\":"));
    assertTrue(serialized.contains("\"eye\":\"left\""));
    assertTrue(serialized.contains("\"result\":[0,0,0,0,0,0,0,0,0,0]"));
  }

  @Test
  public void deserialize_method_should_return_instance_of_DCMUltrasoundDataSourceResult() {
    String serialized = DCMUltrasoundDataSourceResult.serialize(this.result);
    DCMUltrasoundDataSourceResult deserialized = DCMUltrasoundDataSourceResult.deserialize(serialized);

    assertTrue(deserialized instanceof DCMUltrasoundDataSourceResult);
  }

  @Test
  public void getGsonBuilder_should_return_builder() {
    assertNotNull(DCMRetinographyDataSourceResult.getGsonBuilder());
  }

}
