package org.ccem.otus.permissions.utils;

import com.google.gson.*;
import org.ccem.otus.permissions.enums.PermissionMapping;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.servlet.ServletRequest;
import java.lang.reflect.Type;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonObject.class, JsonPrimitive.class, PermissionMapping.class})
public class PermissionAdapterTest {
  private static final String EMAIL = "otus@gmail.com";
  private static final String OBJECT_TYPE = "SurveyGroupPermission";

  @InjectMocks
  private PermissionAdapter permissionAdapter = PowerMockito.spy(new PermissionAdapter());

  @Mock
  private Type typeOfSrc;
  @Mock
  private JsonSerializationContext context;
  @Mock
  private JsonElement jsonElement;
  @Mock
  private Permission src = new Permission();


  @Before
  public void setUp() throws Exception {
    Whitebox.setInternalState(src, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(src, "email", EMAIL);
  }

  @Test
  public void method_serialize_should_return_json_of_JsonElement() {
    when(context.serialize(src)).thenReturn(jsonElement);
    assertTrue(permissionAdapter.serialize(src, typeOfSrc, context) instanceof JsonElement);
  }

  @Test
  public void method_deserialize_should_return_expected_values() {
    JsonElement jsonElement = new JsonObject();
    ((JsonObject) jsonElement).addProperty("objectType", "SurveyGroupPermission");

    assertTrue(permissionAdapter.deserialize(jsonElement, typeOfSrc, new JsonDeserializationContextForTest()) instanceof Permission);
  }
}

class JsonDeserializationContextForTest implements JsonDeserializationContext {
  @Override
  public SurveyGroupPermission deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
    return SurveyGroupPermission.getGsonBuilder().create().fromJson(json, SurveyGroupPermission.class);
  }
}
