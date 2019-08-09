package br.org.otus.staticVariable;

import br.org.otus.rest.Response;
import org.ccem.otus.model.survey.activity.variables.StaticVariableRequest;
import org.ccem.otus.model.survey.activity.variables.StaticVariableRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class StaticVariableResourceTest {

  private static final String INFO_VARIABLE_PARAMS = "{\"recruitmentNumber\":2047555,\"variables\":[{\"identification\":123456,\"name\":\"tst1\",\"value\":\"Text\",\"sending\":null}]}";


  @InjectMocks
  private StaticVariableResource staticVariableResource;

  @Mock
  private StaticVariableFacade staticVariableFacade;

  @Mock
  private StaticVariableRequestDTO staticVariableRequestDTO;

  @Test
  public void method_listVariables_should_return_variablesJson(){
    staticVariableRequestDTO = StaticVariableRequestDTO.deserialize(INFO_VARIABLE_PARAMS);
    when(staticVariableFacade.listVariables(INFO_VARIABLE_PARAMS)).thenReturn(staticVariableRequestDTO);
    String listVariablesExpected = new Response().buildSuccess(staticVariableRequestDTO).toJson();
    assertEquals(listVariablesExpected, staticVariableResource.listVariables(INFO_VARIABLE_PARAMS));
  }
}