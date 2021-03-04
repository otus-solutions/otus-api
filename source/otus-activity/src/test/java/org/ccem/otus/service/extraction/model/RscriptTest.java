package org.ccem.otus.service.extraction.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class RscriptTest {

  private static final String R_SCRIPT_NAME = "script";
  private static final String SCRIPT = "function(x){return(x)}";
  private static final String R_SCRIPT_JSON = "{}";

  private Rscript rscript;

  @Before
  public void setUp() throws Exception {
    rscript = new Rscript();
    Whitebox.setInternalState(rscript, "name", R_SCRIPT_NAME);
    Whitebox.setInternalState(rscript, "script", SCRIPT);
  }

  @Test
  public void getters_check(){
    assertEquals(R_SCRIPT_NAME, rscript.getName());
    assertEquals(SCRIPT, rscript.getScript());
  }

  @Test
  public void deserialize_method_should_return_Rscript_instance(){
    assertTrue(Rscript.deserialize(R_SCRIPT_JSON) instanceof Rscript);
  }
}
