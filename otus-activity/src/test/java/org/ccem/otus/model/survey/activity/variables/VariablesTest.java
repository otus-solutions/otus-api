package org.ccem.otus.model.survey.activity.variables;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
@RunWith(PowerMockRunner.class)
public class VariablesTest {
    private static final long IDENTIFICATION = Long.parseLong("123456");
    private static final String NAME = "tst1";
    private static final String VALUE = "Text";
    private static final String SENDING = "1";

    private Variables variables;
    private  String variablesJson;

    @Before
    public void setUp() {
        variables = new Variables();
        variables.setIdentification(IDENTIFICATION);
        variables.setName(NAME);
        variables.setValue(VALUE);
        variables.setSending(SENDING);
        variablesJson = Variables.serialize(variables);
    }

    @Test
    public void unitTest_for_evoke_getters() {
        assertEquals(IDENTIFICATION,variables.getIdentification());
        assertEquals(NAME,variables.getName());
        assertEquals(VALUE,variables.getValue());
        assertEquals(SENDING,variables.getSending());
    }

    @Test
    public void serializeMethod_should_convert_objectModel_to_JsonString() {
        assertTrue(Variables.serialize(variables) instanceof String);
    }

    @Test
    public void deserializeMethod_shold_convert_JsonString_to_objectModel() {
        assertTrue(Variables.deserialize(variablesJson) instanceof Variables);
    }
}