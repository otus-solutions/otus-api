package org.ccem.otus.model.survey.activity.variables;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
@RunWith(PowerMockRunner.class)
public class StaticVariableRequestDTOTest {
    private static final long RN = Long.parseLong("2047555");
    private static final String variablesDTOJson = "{recruitmentNumber:2047555, variables:[{identification: 123456, name: tst1,value: Text, sending: 1},{identification: 987456,name: tst1,value: Text, sending: 9}]}";
    private static final String variablesJson = "{identification: 123456, name: tst1,value: Text, sending: 1}";

    private StaticVariableRequestDTO staticVariableRequestDTO;
    private ArrayList<StaticVariableRequest> staticVariableRequestArrayList;

    @Mock
    private StaticVariableRequest staticVariableRequest;

    @Before
    public void setUp() {
          staticVariableRequestDTO = StaticVariableRequestDTO.deserialize(variablesDTOJson);
          staticVariableRequest = StaticVariableRequest.deserialize(variablesJson);
     }

    @Test
    public void getRecruitmentNumberMethod_should_return_recruitmentNumber() {
        assertEquals(RN, staticVariableRequestDTO.getRecruitmentNumber());
    }

    @Test
    public void getVariablesListMethod_should_return_Array_Variables() {
        staticVariableRequestArrayList = staticVariableRequestDTO.getVariablesList();

        assertEquals(StaticVariableRequest.serialize(staticVariableRequest), StaticVariableRequest.serialize(staticVariableRequestArrayList.get(0)));
    }

    @Test
    public void serializeMethod_should_convert_objectModel_to_JsonString() {
         assertTrue(StaticVariableRequestDTO.serialize(staticVariableRequestDTO) instanceof String);
    }

    @Test
    public void deserializeMethod_shold_convert_JsonString_to_objectModel() {
        assertTrue(StaticVariableRequestDTO.deserialize(variablesDTOJson) instanceof StaticVariableRequestDTO);
    }

}