package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;
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
@PrepareForTest({SerializableModelWithID.class, GsonBuilder.class, ObjectIdAdapter.class, ObjectIdToStringAdapter.class})
public class SerializableModelWithIDTest {

  private class FakeModel extends SerializableModelWithID { }

  private FakeModel serializableModelWithId;
  private GsonBuilder builder;
  private ObjectIdAdapter objectIdAdapter;
  private ObjectIdToStringAdapter objectIdToStringAdapter;

  @Before
  public void setUp() throws Exception {
    serializableModelWithId = new FakeModel();

    builder = PowerMockito.spy(new GsonBuilder());
    PowerMockito.whenNew(GsonBuilder.class).withNoArguments().thenReturn(builder);

    objectIdAdapter = PowerMockito.spy(new ObjectIdAdapter());
    PowerMockito.whenNew(ObjectIdAdapter.class).withNoArguments().thenReturn(objectIdAdapter);

    objectIdToStringAdapter = PowerMockito.spy(new ObjectIdToStringAdapter());
    PowerMockito.whenNew(ObjectIdToStringAdapter.class).withNoArguments().thenReturn(objectIdToStringAdapter);
  }

  @Test
  public void deserializeStaticMethod_should_convert_JsonString_to_objectModel() {
    assertTrue(SerializableModelWithID.deserialize("{}", Object.class) instanceof Object);
  }

  @Test
  public void getGsonBuilder_return_GsonBuilder_instance(){
    assertTrue(SerializableModelWithID.getGsonBuilder() instanceof GsonBuilder);
  }

  @Test
  public void getFrontGsonBuilder_return_GsonBuilder_instance(){
    assertTrue(SerializableModelWithID.getFrontGsonBuilder() instanceof GsonBuilder);
  }

  @Test
  public void serialize_method_should_convert_ObjectModel_to_JsonString_and_register_ObjectIdAdapter(){
    assertTrue(serializableModelWithId.serialize() instanceof String);
    verify(builder, Mockito.times(1)).registerTypeAdapter(ObjectId.class, objectIdAdapter);
  }

  @Test
  public void getFrontGsonBuilderNonStatic_method_should_return_register_ObjectIdToStringAdapter(){
    assertTrue(serializableModelWithId.getFrontGsonBuilderNonStatic() instanceof GsonBuilder);
    verify(builder, Mockito.times(1)).registerTypeAdapter(ObjectId.class, objectIdToStringAdapter);
  }
}
