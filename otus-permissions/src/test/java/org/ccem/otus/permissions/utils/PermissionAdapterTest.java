package org.ccem.otus.permissions.utils;

import com.google.gson.*;
import org.ccem.otus.permissions.model.user.Permission;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Type;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PermissionAdapter.class})
public class PermissionAdapterTest {
  private static final String EMAIL = "otus@gmail.com";
  private static final String OBJECT_TYPE = "objectType";
  private static final String JSON = "{\"groups\":[\"A\",\"A\",\"C\"],\"_id\":{\"email\":\"teste@gmail.com\",\"objectType\":\"SurveyGroupPermission\"},\"email\":\"teste@gmail.com\",\"objectType\":\"SurveyGroupPermission\"}";

  @InjectMocks
  private PermissionAdapter permissionAdapter = PowerMockito.spy(new PermissionAdapter());

  @Mock
  private Type typeOfSrc;
  @Mock
  private JsonSerializationContext context;
  @Mock
  private JsonElement jsonElement;
  private JsonDeserializationContext contextDeserialize;
  private Permission src = new Permission();

  @Before
  public void setUp() throws Exception {
    Whitebox.setInternalState(src, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(src, "email", EMAIL);
  }

  @Test
  public void serialize() {
    when(context.serialize(src)).thenReturn(jsonElement);
    assertTrue(permissionAdapter.serialize(src, typeOfSrc, context) instanceof JsonElement);
  }

//  @Test
//  public void deserialize() {
//    when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
//    when(jsonObject.get(OBJECT_TYPE)).thenReturn(jsonElement);
//    when((JsonPrimitive)jsonElement).thenReturn(prim);
//
//    permissionAdapter.deserialize(jsonElement,typeOfSrc,contextDeserialize);
//  }
}