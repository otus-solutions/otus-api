package org.ccem.otus.model.dataSources.dcm.retinography;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class DCMRetinographyDataSourceResultTest {

  private DCMRetinographyDataSourceResult result;
  private String RESPONSE_TO_RETINOGRAPHY = "[{\"date\":\"2019-09-09T17:40:34.699Z\",eye\":\"left\",result\":\"R0lGODlhPQBEAPeoAJos595kzAP\"}]";

  @Before
  public void setUp() {
    this.result = new DCMRetinographyDataSourceResult();
    Whitebox.setInternalState(this.result, "date", LocalDateTime.now());
    Whitebox.setInternalState(this.result, "eye", "left");
    Whitebox.setInternalState(this.result, "result", "R0lGODlhPQBEAPeoAJos595kzAP");
  }

  @Test
  public void serialize_method_should_return_string_with_values_expected() {
    String serialized = DCMRetinographyDataSourceResult.serialize(this.result);

    assertTrue(serialized.contains("\"date\":"));
    assertTrue(serialized.contains("\"eye\":\"left\""));
    assertTrue(serialized.contains("\"result\":\"R0lGODlhPQBEAPeoAJos595kzAP\""));
  }

  @Test
  public void deserialize_method_should_return_instance_of_DCMRetinographyDataSourceResult() {
    String serialized = DCMRetinographyDataSourceResult.serialize(this.result);
    DCMRetinographyDataSourceResult deserialized = DCMRetinographyDataSourceResult.deserialize(serialized);

    assertTrue(deserialized instanceof DCMRetinographyDataSourceResult);
  }

  @Test
  public void deserializeList_method_should_return_instance_of_DCMRetinographyDataSourceResult() {
    List<DCMRetinographyDataSourceResult> results = DCMRetinographyDataSourceResult.deserializeList(RESPONSE_TO_RETINOGRAPHY);

    assertTrue(results.get(0) instanceof DCMRetinographyDataSourceResult);
  }

  @Test
  public void getGsonBuilder_should_return_builder() {
    assertNotNull(DCMRetinographyDataSourceResult.getGsonBuilder());
  }

}
