package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class SerializableModelTest {

  @Test
  public void deserializeStaticMethod_should_convert_JsonString_to_objectModel() {
    assertTrue(SerializableModel.deserialize("{}", Object.class) instanceof Object);
  }

}
