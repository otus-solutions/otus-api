package org.ccem.otus.model.dataSources.dcm.retinography;

import org.junit.Before;
import org.powermock.reflect.Whitebox;

public class DCMRetinographyDataSourceResultTest {

  private DCMRetinographyDataSourceResult result;

  @Before
  public void setUp() {
    this.result = new DCMRetinographyDataSourceResult();
    Whitebox.setInternalState(this.result, "date", "");
    Whitebox.setInternalState(this.result, "eye", "left");
    Whitebox.setInternalState(this.result, "result", "left");
  }

  public void serialize_method_should_return_string_with_values_expected() {

  }

  public void deserialize_method_() {

  }

  public void getGsonBuilder_method_() {

  }

}
