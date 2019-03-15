package org.ccem.otus.permissions.persistence.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.utils.PermissionAdapter;

import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserPermissionDTO {

  private ArrayList<Permission> permissions;

  public void concatenatePermissions(UserPermissionDTO userPermissionDTO) {
    if(userPermissionDTO.getPermissions() != null) {
      for (int i = 0; i < this.permissions.size(); i++) {
        int finalI = i;
        List<Permission> filtered = userPermissionDTO.getPermissions().stream().filter(userPermission -> this.permissions.get(finalI).getObjectType().equals(userPermission.getObjectType())).collect(Collectors.toList());

        if (!filtered.isEmpty()) {
          this.permissions.remove(i);
          this.permissions.add(filtered.get(0));
        }
      }
    }
  }

  public static UserPermissionDTO deserialize(String UserPermissionDTOJson) {
    UserPermissionDTO userPermissionsDTO = UserPermissionDTO.getGsonBuilder().create().fromJson(UserPermissionDTOJson, UserPermissionDTO.class);
    return userPermissionsDTO;
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
      jsonObject.put("permissions",jsonArray);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    UserPermissionDTO userPermissionsDTO = UserPermissionDTO.getGsonBuilder().create().fromJson(String.valueOf(jsonObject), UserPermissionDTO.class);
    return userPermissionsDTO;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Permission.class, new PermissionAdapter());
    builder.disableHtmlEscaping();
    return builder;
  }

  public ArrayList<Permission> getPermissions() {
    return permissions;
  }

}
