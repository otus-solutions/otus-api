package org.ccem.otus.model.enums;

import org.ccem.otus.enums.DataSourceMapping;
import org.ccem.otus.model.dataSources.ActivityDataSource;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class DataSourceMappingTest {

    @Test
    public void method_get_enum_by_object_type() throws Exception {
        assertTrue(DataSourceMapping.getEnumByObjectType("Participant").getItemClass() == ParticipantDataSource.class);
        assertTrue(DataSourceMapping.getEnumByObjectType("Activity").getItemClass() == ActivityDataSource.class);
    }

    @Test(expected=RuntimeException.class)
    public void methodAddResult() throws Exception {
       DataSourceMapping.getEnumByObjectType("fakeDataSource").getItemClass();
    }
}
