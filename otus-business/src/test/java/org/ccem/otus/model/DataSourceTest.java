package org.ccem.otus.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class DataSourceTest {

    private static JsonArray elements = new JsonArray();
    private static Set<DataSourceElement> dataSourceElements;

    private static final int SIZE = 1;

    @Mock
    private DataSource dataSource;


    @Before
    public void setUp() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", "TESTE");
        jsonObject.addProperty("extractionValue", "1");
        elements.add(jsonObject);
    }

    @Test
    public void should_method_getDataAsSet_return_a_set() {
        dataSource = new DataSource("ID","DATASOURCE", elements);
        assertEquals(dataSource.getDataAsSet().isEmpty(), Boolean.FALSE);
        assertEquals(dataSource.getDataAsSet().size(), SIZE);
    }
}