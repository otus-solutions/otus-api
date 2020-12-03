package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class StageDtoTest {

  private static final String ACRONYM = "ABC";

  private StageDto stageDto;


  @Before
  public void setUp(){
    stageDto = new StageDto();
    Whitebox.setInternalState(stageDto, "acronym", ACRONYM);
  }

  @Test
  public void getters_test(){
    assertEquals(ACRONYM, stageDto.getAcronym());
    assertEquals(null, stageDto.getStageIdsToAdd());
    assertEquals(null, stageDto.getStageIdsToRemove());
  }

  @Test
  public void deserializeStaticMethod_should_convert_JsonString_to_objectModel() {
    assertTrue(StageDto.deserialize("{}") instanceof StageDto);
  }
}
