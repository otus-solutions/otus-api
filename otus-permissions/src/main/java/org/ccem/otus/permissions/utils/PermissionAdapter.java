package org.ccem.otus.permissions.utils;

import java.lang.reflect.Type;

import org.ccem.otus.permissions.enums.PermissionMapping;
import org.ccem.otus.permissions.model.user.Permission;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PermissionAdapter implements JsonDeserializer<Permission>, JsonSerializer<Permission> {
  private static final String OBJECT_TYPE = "objectType";

  public PermissionAdapter() {
  }

  public JsonElement serialize(Permission src, Type typeOfSrc, JsonSerializationContext context) {
    return context.serialize(src);
  }

  public Permission deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonPrimitive prim = (JsonPrimitive) json.getAsJsonObject().get(OBJECT_TYPE);
    String objectType = prim.getAsString();
    return (Permission) context.deserialize(json, PermissionMapping.getEnumByObjectType(objectType).getItemClass());
  }
}
