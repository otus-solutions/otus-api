package org.ccem.otus.permissions.persistence.user;

import java.util.Iterator;
import java.util.List;

import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.utils.PermissionAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;

public class UserPermissionDTO {

  private List<Permission> permissions;

  public void concatenatePermissions(UserPermissionDTO userPermissionDTO) {
    for (int i = 0; i < this.permissions.size(); i++) {
      Iterator<Permission> iterator = userPermissionDTO.getPermissions().iterator();
      while (iterator.hasNext()) {
        Permission permission = iterator.next();
        if (this.permissions.get(i).getObjectType().equals(permission.getObjectType()))
          this.permissions.set(i,permission);
      }
    }
  }

  public static UserPermissionDTO deserialize(String UserPermissionDTOJson) {
    return UserPermissionDTO.getGsonBuilder().create().fromJson(UserPermissionDTOJson, UserPermissionDTO.class);
  }

  public static String serialize(UserPermissionDTO userPermissionDTO) {
    return UserPermissionDTO.getGsonBuilder().create().toJson(userPermissionDTO);
  }

  public static UserPermissionDTO deserializeSinglePermission(String userPermissionJson) {
    final JSONArray jsonArray = new JSONArray();
    try {
      jsonArray.put(new JSONObject(userPermissionJson));
    } catch (JSONException e) {
      e.printStackTrace();
    }
    final JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("permissions", jsonArray);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    UserPermissionDTO userPermissionsDTO = UserPermissionDTO.getGsonBuilder().create()
        .fromJson(String.valueOf(jsonObject), UserPermissionDTO.class);
    return userPermissionsDTO;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Permission.class, new PermissionAdapter());
    builder.disableHtmlEscaping();
    return builder;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

}
