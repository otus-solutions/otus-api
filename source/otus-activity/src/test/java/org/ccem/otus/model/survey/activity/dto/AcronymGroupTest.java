package org.ccem.otus.model.survey.activity.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class AcronymGroupTest {

  private static final String ACRONYM = "ABC";

  private AcronymGroup acronymGroup;

  @Before
  public void setUp(){
    acronymGroup = new AcronymGroup();
    Whitebox.setInternalState(acronymGroup, "acronym", ACRONYM);
  }

  @Test
  public void getters_test(){
    assertEquals(ACRONYM, acronymGroup.getAcronym());
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel() {
    assertTrue(AcronymGroup.deserialize("{}") instanceof AcronymGroup);
  }
}
