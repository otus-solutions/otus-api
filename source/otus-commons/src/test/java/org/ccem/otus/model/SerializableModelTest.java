package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SerializableModel.class, GsonBuilder.class})
public class SerializableModelTest {

  private static final int NUM_TYPE_ADAPTERS = 2;
  private static final String JSON = "{}";

  private class FakeModel extends SerializableModel {
    @Override
    protected void registerSpecificTypeAdapter(GsonBuilder builder){
      registerGsonBuilderLocalDateTimeAdapter(builder);
      registerGsonBuilderLongAdapter(builder);
    }
  }

  private FakeModel serializableModel;
  private GsonBuilder builder;

  @Before
  public void setUp() throws Exception {
    serializableModel = new FakeModel();

    builder = PowerMockito.spy(new GsonBuilder());
    PowerMockito.whenNew(GsonBuilder.class).withNoArguments().thenReturn(builder);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel() {
    assertTrue(SerializableModel.deserialize("{}", Object.class) instanceof Object);
  }

  @Test
  public void serialize_method_should_convert_objectModel_to_JsonString() {
    assertTrue(serializableModel.serialize() instanceof String);
    verify(builder, Mockito.times(NUM_TYPE_ADAPTERS)).registerTypeAdapter(Mockito.any(), Mockito.any());
  }

  @Test
  public void deserializeNonStatic_method_should_convert_objectModel_to_JsonString() {
    assertTrue(serializableModel.deserializeNonStatic(JSON) instanceof FakeModel);
    verify(builder, Mockito.times(NUM_TYPE_ADAPTERS)).registerTypeAdapter(Mockito.any(), Mockito.any());
  }

}
