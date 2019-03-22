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
@PrepareForTest({JsonObject.class,JsonPrimitive.class,PermissionMapping.class})
public class PermissionAdapterTest {
  private static final String EMAIL = "otus@gmail.com";
  private static final String OBJECT_TYPE = "SurveyGroupPermission";
  private static final String JSON = "{\"groups\":[\"A\",\"A\",\"C\"],\"_id\":{\"email\":\"teste@gmail.com\",\"objectType\":\"SurveyGroupPermission\"},\"email\":\"teste@gmail.com\",\"objectType\":\"SurveyGroupPermission\"}";

  @InjectMocks
  private PermissionAdapter permissionAdapter = PowerMockito.spy(new PermissionAdapter());

  @Mock
  private Type typeOfSrc;
  @Mock
  private JsonSerializationContext context;
  @Mock
  private JsonElement jsonElement;
  @Mock
  private PermissionMapping permissionMapping;
  private JsonDeserializationContext contextDeserialize;
  private Permission src = new Permission();
  @Mock
  private JsonObject jsonObject;


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
//    Class<? extends Permission> permission = null;
//    String objectType = OBJECT_TYPE;
//    JsonElement prim = PowerMockito.mock(JsonPrimitive.class);
//    PermissionMapping permissionMapping1 = PowerMockito.mock(PermissionMapping.class);
//    when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
//    when(jsonObject.get(OBJECT_TYPE)).thenReturn(prim);
//    when(prim.getAsString()).thenReturn(OBJECT_TYPE);
//    when(permissionMapping1.getEnumByObjectType(OBJECT_TYPE)).thenReturn(permissionMapping);
//    when(permissionMapping.getItemClass()).thenReturn();
//
//    permissionAdapter.deserialize(jsonElement,typeOfSrc,contextDeserialize);
//
////     assertTrue(permissionAdapter.deserialize(jsonElement,typeOfSrc,contextDeserialize) instanceof Permission);
//  }
}