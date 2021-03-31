package br.org.otus.extraction.rest;

import br.org.otus.extraction.RscriptFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class RscriptResourceTest {

  private static final String R_SCRIPT_JSON = "{}";
  private static final String R_SCRIPT_NAME = "script";

  @InjectMocks
  private RscriptResource rscriptResource;

  @Mock
  private RscriptFacade rscriptFacade;

  @Test
  public void createOrUpdate_method_should_call_same_method_from_RscriptFacade(){
    rscriptResource.createOrUpdate(R_SCRIPT_JSON);
    Mockito.verify(rscriptFacade, Mockito.times(1)).createOrUpdate(R_SCRIPT_JSON);
  }

  @Test
  public void get_method_should_call_same_method_from_RscriptFacade(){
    rscriptResource.get(R_SCRIPT_NAME);
    Mockito.verify(rscriptFacade, Mockito.times(1)).get(R_SCRIPT_NAME);
  }

  @Test
  public void delete_method_should_call_same_method_from_RscriptFacade(){
    rscriptResource.delete(R_SCRIPT_NAME);
    Mockito.verify(rscriptFacade, Mockito.times(1)).delete(R_SCRIPT_NAME);
  }
}
