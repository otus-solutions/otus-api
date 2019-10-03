package org.ccem.otus.enums;

import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class DataSourceMappingTest {

    @Test
    public void method_getEnumByObjectType_should_return_ParticipantDataSource() throws Exception {
        assertTrue(DataSourceMapping.getEnumByObjectType("Participant").getItemClass() == ParticipantDataSource.class);
    }

    @Test
    public void method_getEnumByObjectType_should_return_ActivityDataSource() throws Exception {
        assertTrue(DataSourceMapping.getEnumByObjectType("Activity").getItemClass() == ActivityDataSource.class);
    }

    @Test(expected=RuntimeException.class)
    public void method_getEnumByObjectType_should_trow_RuntimeException() throws Exception {
       DataSourceMapping.getEnumByObjectType("fakeDataSource");
    }

}
