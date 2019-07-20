package org.ccem.otus.model.survey.activity.variables;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
@RunWith(PowerMockRunner.class)
public class StaticVariableRequestTest {
    private static final long IDENTIFICATION = Long.parseLong("123456");
    private static final String NAME = "tst1";
    private static final String VALUE = "Text";
    private static final String SENDING = "1";

    private StaticVariableRequest staticVariableRequest;
    private  String variablesJson;

    @Before
    public void setUp() {
        staticVariableRequest = new StaticVariableRequest();
        staticVariableRequest.setIdentification(IDENTIFICATION);
        staticVariableRequest.setName(NAME);
        staticVariableRequest.setValue(VALUE);
        staticVariableRequest.setSending(SENDING);
        variablesJson = StaticVariableRequest.serialize(staticVariableRequest);
    }

    @Test
    public void unitTest_for_evoke_getters() {
        assertEquals(IDENTIFICATION, staticVariableRequest.getIdentification());
        assertEquals(NAME, staticVariableRequest.getName());
        assertEquals(VALUE, staticVariableRequest.getValue());
        assertEquals(SENDING, staticVariableRequest.getSending());
    }

    @Test
    public void serializeMethod_should_convert_objectModel_to_JsonString() {
        assertTrue(StaticVariableRequest.serialize(staticVariableRequest) instanceof String);
    }

    @Test
    public void deserializeMethod_shold_convert_JsonString_to_objectModel() {
        assertTrue(StaticVariableRequest.deserialize(variablesJson) instanceof StaticVariableRequest);
    }
}