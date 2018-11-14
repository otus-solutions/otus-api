package br.org.otus.laboratory.participant.tube;


import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;


@RunWith(PowerMockRunner.class)

public class TubeTest {

  public static final String OBJECT_TYPE = "Tube";
  public static final String TYPE = "GEL";
  public static final String MOMENT = "FASTING";
  public static final String CODE = "351286082";
  public static final String GROUPNAME = "DEFAULT";
  public static final String ALIQUOTS = null;
  public static final Integer ORDER = 1;
  public static final String TUBE_JSON_STRING = "{\"objectType\":\"Tube\",\"type\":\"GEL\",\"moment\":\"FASTING\",\"code\":\"351286082\",\"groupName\":\"DEFAULT\",\"aliquots\":null,\"order\":1,\"tubeCollectionData\":{\"objectType\":\"TubeCollectionData\",\"isCollected\":true,\"metadata\":\"\",\"operator\":\"email@gmail.com\",\"time\":\"2018-11-12T15:57:34.315Z\"}}";

  private TubeCollectionData tubeCollectionData;

  @InjectMocks
  private Tube tubeFromJson;
  @Mock
  private Aliquot aliquot;
  @Mock
  private SimpleAliquot simpleAliquot;

  @Before
  public void setUp(){
    tubeFromJson = Tube.deserialize(TUBE_JSON_STRING);
  }

  @Test
  public void method_getType_should_return_type() {
    assertEquals(TYPE, tubeFromJson.getType());
  }

  @Test
  public void method_getObjectType_should_return_objectType() {
    assertEquals(OBJECT_TYPE, tubeFromJson.getObjectType());
  }

  @Test
  public void method_getMoment_should_return_moment() {
    assertEquals(MOMENT, tubeFromJson.getMoment());
  }

  @Test
  public void method_getCode_should_return_code() {
    assertEquals(CODE, tubeFromJson.getCode());
  }

  @Test
  public void method_getGroupName_should_return_groupName(){
    assertEquals(GROUPNAME, tubeFromJson.getGroupName());
  }

  @Test
  public void method_getAliquots_should_return_aliquots(){
    assertEquals(ALIQUOTS, tubeFromJson.getAliquots());
  }

  @Test
  public void method_getOrder_should_return_order(){
    assertEquals(ORDER, tubeFromJson.getOrder());
  }

  @Test
  public void method_getTubeCollectionData_should_return_collectionData(){
    assertEquals("TubeCollectionData", tubeFromJson.getTubeCollectionData().getObjectType());
  }

  @Test
  public void method_serialize_should_return_tubeJson(){
    assertEquals(Tube.serialize(tubeFromJson),TUBE_JSON_STRING);
  }
}
