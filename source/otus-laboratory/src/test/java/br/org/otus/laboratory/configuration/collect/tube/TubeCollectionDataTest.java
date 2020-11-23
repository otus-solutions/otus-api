package br.org.otus.laboratory.configuration.collect.tube;

import br.org.otus.laboratory.participant.tube.TubeCollectionData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class TubeCollectionDataTest {

  private static final String OBJECT_TYPE = "TubeCollectionData";
  private static final boolean IS_COLLECTED = false;
  private static final String METADATA = "";
  private static final List<String> CUSTOM_METADATA = new ArrayList<>();
  private static final String OPERATOR = "";
  private static final LocalDateTime TIME = null;

  private TubeCollectionData tubeCollectionData;

  @Before
  public void setUp(){
    tubeCollectionData = new TubeCollectionData();
    Whitebox.setInternalState(tubeCollectionData, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(tubeCollectionData, "isCollected", IS_COLLECTED);
    Whitebox.setInternalState(tubeCollectionData, "metadata", METADATA);
    Whitebox.setInternalState(tubeCollectionData, "customMetadata", CUSTOM_METADATA);
    Whitebox.setInternalState(tubeCollectionData, "operator", OPERATOR);
    Whitebox.setInternalState(tubeCollectionData, "time", TIME);
  }

  @Test
  public void getters_unit_test(){
    assertEquals(OBJECT_TYPE, tubeCollectionData.getObjectType());
    assertEquals(IS_COLLECTED, tubeCollectionData.isCollected());
    assertEquals(METADATA, tubeCollectionData.getMetadata());
    assertEquals(CUSTOM_METADATA, tubeCollectionData.getCustomMetadata());
    assertEquals(OPERATOR, tubeCollectionData.getOperatorEmail());
    assertEquals(TIME, tubeCollectionData.getTime());
  }

}
