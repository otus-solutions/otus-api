package model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class StageTest {

  private static final ObjectId OID = new ObjectId();
  private static final String OBJECT_TYPE = "Stage";
  private static final String NAME = "some stage";

  private Stage stage;

  @Before
  public void setUp(){
    stage = new Stage();
    Whitebox.setInternalState(stage, "id", OID);
    Whitebox.setInternalState(stage, "name", NAME);
  }

  @Test
  public void getters_test(){
    assertEquals(OID, stage.getId());
    assertEquals(OBJECT_TYPE, stage.getObjectType());
    assertEquals(NAME, stage.getName());
    assertEquals(0, stage.getSurveyAcronyms().size());
  }

  @Test
  public void id_setter_test(){
    ObjectId OID2 = new ObjectId("5ab3a88013cdd20490873afe");
    stage.setId(OID2);
    assertEquals(OID2, stage.getId());
  }

  @Test
  public void name_setter_test(){
    final String NAME2 = NAME + "2";
    stage.setName(NAME2);
    assertEquals(NAME2, stage.getName());
  }

  @Test
  public void deserializeStaticMethod_should_convert_JsonString_to_objectModel() {
    assertTrue(Stage.deserialize("{}") instanceof Stage);
  }

}
