package org.ccem.otus.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class DataSourceValuesMappingTest {
  private static final String EXTRACTION_VALUE = "extractionValue";
  private static final String VALUE = "value";
  private static final String DATASOURCE_NAME = "dataSourceName";
  private static final String DATASOURCE_VALUES_MAPPING_JSON = "{\n" +
    "        \"mappingList\" : [\n" +
    "                            [\n" +
    "                                " + DATASOURCE_NAME + ",\n" +
    "                                    {\n" +
    "                                        \"hashMap\":[\n" +
    "                                            [\n" +
    "                                            " + VALUE + "," +
    "                                            " + EXTRACTION_VALUE +
    "                                            ]\n" +
    "                                        ]\n" +
    "                                    }\n" +
    "                            ]\n" +
    "        ]\n" +
    "}";

  @Test
  public void getExtractionValue_should_return_mapped_extraction_value() {
    DataSourceValuesMapping dataSourceValuesMapping = DataSourceValuesMapping.deserialize(DATASOURCE_VALUES_MAPPING_JSON);
    assertEquals(EXTRACTION_VALUE, dataSourceValuesMapping.getExtractionValue(DATASOURCE_NAME, VALUE));
  }

}
