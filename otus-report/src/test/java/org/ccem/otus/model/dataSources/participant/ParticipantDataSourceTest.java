package org.ccem.otus.model.dataSources.participant;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSourceResult;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class ParticipantDataSourceTest {
    private ParticipantDataSource participantDataSource;

    private ParticipantDataSourceResult participantDataSourceResult;

    private ImmutableDate immutableDateInstance;

    @Before
    public void setup(){
        participantDataSource = new ParticipantDataSource();
        participantDataSourceResult = new ParticipantDataSourceResult();

        FieldCenter fieldCenterInstance = new FieldCenter();
        Whitebox.setInternalState(fieldCenterInstance, "acronym", "RS");

        immutableDateInstance = new ImmutableDate("2018-02-22 00:00:00.000");


        participantDataSourceResult = new ParticipantDataSourceResult();
        Whitebox.setInternalState(participantDataSourceResult, "name", "Joao");
        Whitebox.setInternalState(participantDataSourceResult, "sex", "masc");
        Whitebox.setInternalState(participantDataSourceResult, "fieldCenter", fieldCenterInstance);
        Whitebox.setInternalState(participantDataSourceResult, "birthdate", immutableDateInstance);
    }

    @Test
    public void method_addResult_should_add_participantDataSourceResult(){
        participantDataSource.getResult().add(participantDataSourceResult);
        assertEquals(participantDataSource.getResult().get(0).getBirthdate().getValue(),immutableDateInstance.getValue());
    }
}
