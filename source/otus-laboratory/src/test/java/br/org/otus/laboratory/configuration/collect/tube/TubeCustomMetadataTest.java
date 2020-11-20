package br.org.otus.laboratory.configuration.collect.tube;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class TubeCustomMetadataTest {

  private static final String ID = "5a33cb4a28f10d1043710f7d";
  private static final ObjectId OID = new ObjectId(ID);
  private static final String TYPE = "type";
  private static final String OBJECT_TYPE = TubeCustomMetadata.OBJECT_TYPE;
  private static final String VALUE = "value";
  private static final String EXTRACTION_VALUE = "extraction value";

  private TubeCustomMetadata tubeCustomMetadata;

  @Before
  public void setUp(){
    tubeCustomMetadata = new TubeCustomMetadata();
    Whitebox.setInternalState(tubeCustomMetadata, "id", OID);
    Whitebox.setInternalState(tubeCustomMetadata, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(tubeCustomMetadata, "type", TYPE);
    Whitebox.setInternalState(tubeCustomMetadata, "value", VALUE);
    Whitebox.setInternalState(tubeCustomMetadata, "extractionValue", EXTRACTION_VALUE);
  }

  @Test
  public void getters_unit_test(){
    assertEquals(OID, tubeCustomMetadata.getId());
    assertEquals(OBJECT_TYPE, tubeCustomMetadata.getObjectType());
    assertEquals(OBJECT_TYPE, tubeCustomMetadata.getObjectType());
    assertEquals(VALUE, tubeCustomMetadata.getValue());
    assertEquals(EXTRACTION_VALUE, tubeCustomMetadata.getExtractionValue());
  }

}
